<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="居民" prop="residentId">
        <el-select
            v-model="queryParams.userId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入居民名称"
            :remote-method="remoteGetResidentList"
            :loading="residentLoading"
            style="width: 100%"
        >
          <el-option
              v-for="item in residentList"
              :key="item.id"
              :label="item.residentName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="测量时间" style="width: 308px">
        <el-date-picker
            v-model="daterangeMeasureTime"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="测量类型" prop="measureType">
        <el-select v-model="queryParams.measureType" style="width: 200px" placeholder="请选择测量类型" clearable>
          <el-option
              v-for="dict in health_measure_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属用户" prop="userId">
        <el-input
            v-model="queryParams.userId"
            placeholder="请输入所属用户"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
            v-model="queryParams.createBy"
            placeholder="请输入创建人"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker
            v-model="daterangeCreateTime"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['manage:healthHistoryInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:healthHistoryInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:healthHistoryInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:healthHistoryInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="healthHistoryInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="居民" align="center" prop="residentName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="测量时间" align="center" prop="measureTime" width="180" v-if="columns[2].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.measureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身高" align="center" prop="height" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="体重" align="center" prop="weight" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="收缩压mmHg" align="center" prop="sbp" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="舒张压mmHg" align="center" prop="dbp" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="血糖mmol/L" align="center" prop="bloodGlucose" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="心率次/分" align="center" prop="heartRate" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="测量类型" align="center" prop="measureType" v-if="columns[9].visible">
        <template #default="scope">
          <dict-tag :options="health_measure_type" :value="scope.row.measureType"/>
        </template>
      </el-table-column>
      <el-table-column label="附件" align="center" prop="appendix" width="100" v-if="columns[10].visible">
        <template #default="scope">
          <file-view :file-url="scope.row.appendix"/>
        </template>
      </el-table-column>
      <el-table-column label="所属用户" align="center" prop="userName" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[13].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[15].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:healthHistoryInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:healthHistoryInfo:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改健康记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="healthHistoryInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="居民" prop="residentId">
          <el-select
              v-model="form.residentId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入居民名称"
              :remote-method="remoteGetResidentList"
              :loading="residentLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in residentList"
                :key="item.id"
                :label="item.residentName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="测量时间" prop="measureTime">
          <el-date-picker clearable
                          v-model="form.measureTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择测量时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input-number style="width: 100%;" :min="0" :precision="2" v-model="form.height" placeholder="请输入身高"/>
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input-number style="width: 100%;" :min="0" :precision="2" v-model="form.weight" placeholder="请输入体重"/>
        </el-form-item>
        <el-form-item label="收缩压mmHg" prop="sbp">
          <el-input-number style="width: 100%;" :min="0" v-model="form.sbp" placeholder="请输入收缩压mmHg"/>
        </el-form-item>
        <el-form-item label="舒张压mmHg" prop="dbp">
          <el-input-number style="width: 100%;" :min="0" v-model="form.dbp" placeholder="请输入舒张压mmHg"/>
        </el-form-item>
        <el-form-item label="血糖mmol/L" prop="bloodGlucose">
          <el-input-number style="width: 100%;" :min="0" :precision="2" v-model="form.bloodGlucose"
                           placeholder="请输入血糖mmol/L"/>
        </el-form-item>
        <el-form-item label="心率次/分" prop="heartRate">
          <el-input-number style="width: 100%;" :min="0" v-model="form.heartRate" placeholder="请输入心率次/分"/>
        </el-form-item>
        <el-form-item label="测量类型" prop="measureType">
          <el-select v-model="form.measureType" placeholder="请选择测量类型">
            <el-option
                v-for="dict in health_measure_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件" prop="appendix">
          <file-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="HealthHistoryInfo">
import {
  listHealthHistoryInfo,
  getHealthHistoryInfo,
  delHealthHistoryInfo,
  addHealthHistoryInfo,
  updateHealthHistoryInfo
} from "@/api/manage/healthHistoryInfo";
import {listResidentInfo} from "@/api/manage/residentInfo.js";

const {proxy} = getCurrentInstance();
const {health_measure_type} = proxy.useDict('health_measure_type');

const healthHistoryInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeMeasureTime = ref([]);
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/healthHistoryInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    residentId: null,
    measureTime: null,
    measureType: null,
    userId: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    residentId: [
      {required: true, message: "居民不能为空", trigger: "blur"}
    ],
    measureTime: [
      {required: true, message: "测量时间不能为空", trigger: "blur"}
    ],
    measureType: [
      {required: true, message: "测量类型不能为空", trigger: "change"}
    ],
    userId: [
      {required: true, message: "所属用户不能为空", trigger: "blur"}
    ],
    createBy: [
      {required: true, message: "创建人不能为空", trigger: "blur"}
    ],
    createTime: [
      {required: true, message: "创建时间不能为空", trigger: "blur"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '居民', visible: true},
    {key: 2, label: '测量时间', visible: true},
    {key: 3, label: '身高', visible: true},
    {key: 4, label: '体重', visible: true},
    {key: 5, label: '收缩压mmHg', visible: true},
    {key: 6, label: '舒张压mmHg', visible: true},
    {key: 7, label: '血糖mmol/L', visible: true},
    {key: 8, label: '心率次/分', visible: true},
    {key: 9, label: '测量类型', visible: true},
    {key: 10, label: '附件', visible: true},
    {key: 11, label: '所属用户', visible: true},
    {key: 12, label: '创建人', visible: true},
    {key: 13, label: '创建时间', visible: true},
    {key: 14, label: '更新人', visible: false},
    {key: 15, label: '更新时间', visible: false},
    {key: 16, label: '备注', visible: true},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询健康记录列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeMeasureTime && '' != daterangeMeasureTime) {
    queryParams.value.params["beginMeasureTime"] = daterangeMeasureTime.value[0];
    queryParams.value.params["endMeasureTime"] = daterangeMeasureTime.value[1];
  }
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listHealthHistoryInfo(queryParams.value).then(response => {
    healthHistoryInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    residentId: null,
    measureTime: null,
    height: null,
    weight: null,
    sbp: null,
    dbp: null,
    bloodGlucose: null,
    heartRate: null,
    measureType: null,
    appendix: null,
    userId: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("healthHistoryInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeMeasureTime.value = [];
  daterangeCreateTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加健康记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHealthHistoryInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改健康记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["healthHistoryInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHealthHistoryInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHealthHistoryInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除健康记录编号为"' + _ids + '"的数据项？').then(function () {
    return delHealthHistoryInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(exportUrl.value, {
    ...queryParams.value
  }, `healthHistoryInfo_${new Date().getTime()}.xlsx`)
}

const residentList = ref([]);
const residentLoading = ref(false);
const residentQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  residentName: null,
});
const getResidentList = () => {
  residentLoading.value = true;
  listResidentInfo(residentQueryParams).then(response => {
    residentList.value = response.rows;
    residentLoading.value = false;
  });
};
const remoteGetResidentList = (query) => {
  residentQueryParams.name = query;
  getResidentList();
}

getResidentList()

getList();
</script>
