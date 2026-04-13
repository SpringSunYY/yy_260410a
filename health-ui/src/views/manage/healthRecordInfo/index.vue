<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="档案编号" prop="recordCode">
        <el-input
            v-model="queryParams.recordCode"
            placeholder="请输入档案编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="血型" prop="bloodType">
        <el-select v-model="queryParams.bloodType" style="width: 200px" placeholder="请选择血型" clearable>
          <el-option
              v-for="dict in health_blood_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="RH类型" prop="rhType">
        <el-select v-model="queryParams.rhType" style="width: 200px" placeholder="请选择RH类型" clearable>
          <el-option
              v-for="dict in health_rh_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
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
            v-hasPermi="['manage:healthRecordInfo:add']"
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
            v-hasPermi="['manage:healthRecordInfo:edit']"
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
            v-hasPermi="['manage:healthRecordInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:healthRecordInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="healthRecordInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="居民" align="center" prop="residentName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="档案编号" align="center" prop="recordCode" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="血型" align="center" prop="bloodType" v-if="columns[3].visible">
        <template #default="scope">
          <dict-tag :options="health_blood_type" :value="scope.row.bloodType"/>
        </template>
      </el-table-column>
      <el-table-column label="RH类型" align="center" prop="rhType" v-if="columns[4].visible">
        <template #default="scope">
          <dict-tag :options="health_rh_type" :value="scope.row.rhType"/>
        </template>
      </el-table-column>
      <el-table-column label="既往病史" align="center" prop="pastHistory" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="家族病史" align="center" prop="familyHistory" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="附件" align="center" prop="appendix" width="100" v-if="columns[7].visible">
        <template #default="scope">
          <file-view :file-url="scope.row.appendix"/>
        </template>
      </el-table-column>
      <el-table-column label="所属用户" align="center" prop="userName" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[10].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[12].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:healthRecordInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:healthRecordInfo:remove']">删除
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

    <!-- 添加或修改健康档案对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="healthRecordInfoRef" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="档案编号" prop="recordCode">
          <el-input :readonly="form.id!=null  " v-model="form.recordCode" placeholder="请输入档案编号"/>
        </el-form-item>
        <el-form-item label="血型" prop="bloodType">
          <el-select v-model="form.bloodType" placeholder="请选择血型">
            <el-option
                v-for="dict in health_blood_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="RH类型" prop="rhType">
          <el-select v-model="form.rhType" placeholder="请选择RH类型">
            <el-option
                v-for="dict in health_rh_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="既往病史" prop="pastHistory">
          <el-input v-model="form.pastHistory" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="家族病史" prop="familyHistory">
          <el-input v-model="form.familyHistory" type="textarea" placeholder="请输入内容"/>
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

<script setup name="HealthRecordInfo">
import {
  addHealthRecordInfo,
  delHealthRecordInfo,
  getHealthRecordInfo,
  listHealthRecordInfo,
  updateHealthRecordInfo
} from "@/api/manage/healthRecordInfo";
import {listResidentInfo} from "@/api/manage/residentInfo.js";

const {proxy} = getCurrentInstance();
const {health_rh_type, health_blood_type} = proxy.useDict('health_rh_type', 'health_blood_type');

const healthRecordInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/healthRecordInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    residentId: null,
    recordCode: null,
    bloodType: null,
    rhType: null,
    userId: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    residentId: [
      {required: true, message: "居民不能为空", trigger: "blur"}
    ],
    recordCode: [
      {required: true, message: "档案编号不能为空", trigger: "blur"}
    ],
    bloodType: [
      {required: true, message: "血型不能为空", trigger: "change"}
    ],
    rhType: [
      {required: true, message: "RH类型不能为空", trigger: "change"}
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
    {key: 2, label: '档案编号', visible: true},
    {key: 3, label: '血型', visible: true},
    {key: 4, label: 'RH类型', visible: true},
    {key: 5, label: '既往病史', visible: true},
    {key: 6, label: '家族病史', visible: true},
    {key: 7, label: '附件', visible: true},
    {key: 8, label: '所属用户', visible: true},
    {key: 9, label: '创建人', visible: true},
    {key: 10, label: '创建时间', visible: true},
    {key: 11, label: '更新人', visible: false},
    {key: 12, label: '更新时间', visible: false},
    {key: 13, label: '备注', visible: true},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询健康档案列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listHealthRecordInfo(queryParams.value).then(response => {
    healthRecordInfoList.value = response.rows;
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
    recordCode: null,
    bloodType: null,
    rhType: null,
    pastHistory: null,
    familyHistory: null,
    appendix: null,
    userId: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("healthRecordInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
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
  title.value = "添加健康档案";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHealthRecordInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改健康档案";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["healthRecordInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHealthRecordInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHealthRecordInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除健康档案编号为"' + _ids + '"的数据项？').then(function () {
    return delHealthRecordInfo(_ids);
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
  }, `healthRecordInfo_${new Date().getTime()}.xlsx`)
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
