<template>
  <div class="app-container">
    <!-- 查询条件卡片 -->
    <el-card class="search-card" shadow="hover">
      <el-form :model="tableQuery" ref="queryRef" :inline="true" label-width="80px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="居民" prop="residentId">
              <el-select
                  v-model="tableQuery.residentId"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入居民名称"
                  :remote-method="remoteGetResidentList"
                  :loading="residentLoading"
                  style="width: 100%"
                  clearable
              >
                <el-option
                    v-for="item in residentList"
                    :key="item.id"
                    :label="item.residentName"
                    :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="测量时间" prop="measureTime">
              <el-date-picker
                  v-model="daterangeMeasureTime"
                  value-format="YYYY-MM-DD"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  style="width: 100%"
                  clearable
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="测量类型" prop="measureType">
              <el-select v-model="tableQuery.measureType" placeholder="请选择测量类型" clearable>
                <el-option
                    v-for="dict in health_measure_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleQuery">查询</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 统计图表区域 -->
    <el-row :gutter="20" class="chart-container">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="chart-card">
          <div class="chart-placeholder">
            <LineAvgCharts
                :chart-data="bloodLipidsStatisticsData"
                :chart-name="bloodLipidsStatisticsName"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="chart-card">
          <div class="chart-placeholder">
            <BarAvgCharts
                :chart-data="bloodPressureStatisticsData"
                :chart-name="bloodPressureStatisticsName"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="chart-card">
          <div class="chart-placeholder">
            <BarAutoCarouselCharts
                :chart-data="bloodGlucoseStatisticsData"
                :chart-name="bloodGlucoseStatisticsName"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 数据表格区域 -->
    <el-card shadow="hover" class="table-card">
      <el-table v-loading="loading" :data="healthHistoryInfoList">
        <el-table-column label="编号" align="center" prop="id" width="80"/>
        <el-table-column label="居民" align="center" prop="residentName"/>
        <el-table-column label="测量时间" align="center" prop="measureTime" width="160">
          <template #default="scope">
            <span>{{ parseTime(scope.row.measureTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="身高(cm)" align="center" prop="height" width="100"/>
        <el-table-column label="体重(kg)" align="center" prop="weight" width="100"/>
        <el-table-column label="收缩压" align="center" prop="sbp" width="100"/>
        <el-table-column label="舒张压" align="center" prop="dbp" width="100"/>
        <el-table-column label="血糖" align="center" prop="bloodGlucose" width="100"/>
        <el-table-column label="心率" align="center" prop="heartRate" width="100"/>
        <el-table-column label="血脂" align="center" prop="bloodLipids" width="100"/>
        <el-table-column label="血压" align="center" prop="bloodPressure" width="100"/>
        <el-table-column label="测量类型" align="center" width="100">
          <template #default="scope">
            <dict-tag :options="health_measure_type" :value="scope.row.measureType"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
          v-show="total>0"
          :total="total"
          v-model:page="tableQuery.pageNum"
          v-model:limit="tableQuery.pageSize"
          @pagination="getList"
      />
    </el-card>
  </div>
</template>

<script setup name="healthStatisticsInfo">
import {listHealthHistoryInfo, statisticsHealthHistoryInfo} from "@/api/manage/healthHistoryInfo";
import {listResidentInfo} from "@/api/manage/residentInfo.js";
import LineAvgCharts from "@/components/Echarts/LineAvgCharts.vue";
import BarAvgCharts from "@/components/Echarts/BarAvgCharts.vue";
import BarAutoCarouselCharts from "@/components/Echarts/BarAutoCarouselCharts.vue";

const {proxy} = getCurrentInstance();
const {health_measure_type} = proxy.useDict('health_measure_type');

const loading = ref(false);
const total = ref(0);
const daterangeMeasureTime = ref([]);
const healthHistoryInfoList = ref([]);

const data = reactive({
  tableQuery: {
    pageNum: 1,
    pageSize: 10,
    residentId: null,
    measureType: null,
    params: {},
  },
  statisticsQuery: {
    residentId: null,
    measureType: null,
    startTime: null,
    endTime: null,
  }
});

const {tableQuery, statisticsQuery} = toRefs(data);

/** 查询健康记录列表 */
function getList() {
  if (daterangeMeasureTime.value && daterangeMeasureTime.value.length === 2) {
    tableQuery.value.params.beginMeasureTime = daterangeMeasureTime.value[0];
    tableQuery.value.params.endMeasureTime = daterangeMeasureTime.value[1];
  } else {
    tableQuery.value.params = {};
  }
  if (!tableQuery.value.residentId && residentList.value && residentList.value.length > 0) {
    tableQuery.value.residentId = residentList.value[0].id
  }
  loading.value = true;
  listHealthHistoryInfo(tableQuery.value).then(response => {
    healthHistoryInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

const bloodGlucoseStatisticsData = ref({
  names: [],
  values: []
})
const bloodGlucoseStatisticsName = ref('血糖')
const bloodPressureStatisticsData = ref({
  names: [],
  values: []
})
const bloodPressureStatisticsName = ref('血压')
const bloodLipidsStatisticsData = ref({
  names: [],
  values: []
})
const bloodLipidsStatisticsName = ref('血脂')

function getStatistics() {
  statisticsQuery.value.residentId = tableQuery.value.residentId
  if (!statisticsQuery.value.residentId && residentList.value && residentList.value.length > 0) {
    statisticsQuery.value.residentId = residentList.value[0].id
    tableQuery.value.residentId = residentList.value[0].id
  }
  statisticsQuery.value.measureType = tableQuery.value.measureType
  statisticsQuery.value.startTime = daterangeMeasureTime.value[0]
  statisticsQuery.value.endTime = daterangeMeasureTime.value[1]
  statisticsHealthHistoryInfo(statisticsQuery.value).then(res => {
    if (!res.data.length > 0) return
    let names = []
    let bloodLipids = []
    let bloodPressure = []
    let bloodGlucose = []
    res.data.forEach(item => {
      names.push(item.measureTime)
      bloodLipids.push(item.bloodLipids)
      bloodPressure.push(item.bloodPressure)
      bloodGlucose.push(item.bloodGlucose)
    })
    bloodGlucoseStatisticsData.value = {
      names,
      values: bloodGlucose
    }
    bloodPressureStatisticsData.value = {
      names,
      values: bloodPressure
    }
    bloodLipidsStatisticsData.value = {
      names,
      values: bloodLipids
    }
  })
}

function getQuery() {
  getList()
  getStatistics()
}

/** 搜索按钮操作 */
function handleQuery() {
  tableQuery.value.pageNum = 1;
  getQuery();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeMeasureTime.value = getLast14DaysRange();
  proxy.resetForm("queryRef");
  tableQuery.value = {
    pageNum: 1,
    pageSize: 10,
    userId: null,
    measureType: null,
    createBy: null,
    params: {}
  };
  handleQuery();
}

// 居民列表相关
const residentList = ref([]);
const residentLoading = ref(false);
const residentQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  residentName: null,
});

const getResidentList = async () => {
  residentLoading.value = true;
  await listResidentInfo(residentQueryParams).then(response => {
    residentList.value = response.rows;
    residentLoading.value = false;
  });
};

const remoteGetResidentList = (query) => {
  residentQueryParams.residentName = query;
  getResidentList();
};

/** 获取近14天的日期范围 */
function getLast14DaysRange() {
  const today = new Date();
  const startDate = new Date(today);
  startDate.setDate(today.getDate() - 13);
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };
  return [formatDate(startDate), formatDate(today)];
}

onMounted(async () => {
  daterangeMeasureTime.value = getLast14DaysRange();
  await getResidentList();
  getQuery();
});
</script>

<style scoped lang="scss">
.page-header {
  margin-bottom: 20px;

  h2 {
    margin: 0;
    font-size: 20px;
    font-weight: 600;
    color: #303133;
  }
}

.search-card {
  background: #f5f7fa;
  padding: 16px 20px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.chart-container {
  /* 统计图表区域 */
  background: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 20px;

  .chart-card {
    height: 100%;
    min-height: 380px;
    background: #fff;

    .chart-placeholder {
      height: 320px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

:deep(.el-form-item) {
  margin-bottom: 0;
}

:deep(.el-col) {
  margin-bottom: 18px;
}

@media (max-width: 768px) {
  .page-header h2 {
    font-size: 18px;
  }

  :deep(.el-form-item) {
    margin-bottom: 18px;
  }

  :deep(.el-col) {
    margin-bottom: 0;
  }
}
</style>
