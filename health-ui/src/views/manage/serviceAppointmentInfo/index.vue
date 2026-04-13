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
      <el-form-item label="服务类型" prop="serviceType">
        <el-select v-model="queryParams.serviceType" style="width: 200px" placeholder="请选择服务类型" clearable>
          <el-option
              v-for="dict in healt_service_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预约标题" prop="appointmentTitle">
        <el-input
            v-model="queryParams.appointmentTitle"
            placeholder="请输入预约标题"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约日期" style="width: 308px">
        <el-date-picker
            v-model="daterangeAppointmentTime"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="具体时间" prop="dateType">
        <el-select v-model="queryParams.dateType" style="width: 200px" placeholder="请选择具体时间" clearable>
          <el-option
              v-for="dict in health_date_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" style="width: 200px" placeholder="请选择状态" clearable>
          <el-option
              v-for="dict in health_appointment_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预约医生" prop="appointmentUserId">
        <el-input
            v-model="queryParams.appointmentUserId"
            placeholder="请输入预约医生"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="所属用户" prop="userId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.userId"-->
      <!--            placeholder="请输入所属用户"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
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
            v-hasPermi="['manage:serviceAppointmentInfo:add']"
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
            v-hasPermi="['manage:serviceAppointmentInfo:edit']"
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
            v-hasPermi="['manage:serviceAppointmentInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:serviceAppointmentInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceAppointmentInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="居民" align="center" prop="residentName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="服务类型" align="center" prop="serviceType" v-if="columns[2].visible">
        <template #default="scope">
          <dict-tag :options="healt_service_type" :value="scope.row.serviceType"/>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" align="center" prop="contactMethod" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="预约标题" align="center" prop="appointmentTitle" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="预约原因" align="center" prop="appointmentReason" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="预约日期" align="center" prop="appointmentTime" width="180" v-if="columns[6].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.appointmentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="具体时间" align="center" prop="dateType" v-if="columns[7].visible">
        <template #default="scope">
          <dict-tag :options="health_date_type" :value="scope.row.dateType"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" v-if="columns[8].visible">
        <template #default="scope">
          <dict-tag :options="health_appointment_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="预约医生" align="center" prop="appointmentUserName" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="服务时间" align="center" prop="serviceTime" width="180" v-if="columns[10].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.serviceTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="附件" align="center" prop="appendix" width="100" v-if="columns[11].visible">
        <template #default="scope">
          <file-view :file-url="scope.row.appendix"/>
        </template>
      </el-table-column>
      <el-table-column label="所属用户" align="center" prop="userName" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[16].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[17].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" fixed="right" width="180" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:serviceAppointmentInfo:edit']">修改
          </el-button>
          <el-button link type="primary" @click="handleAppointmentAudit(scope.row)"
                     v-hasPermi="['manage:serviceAppointmentInfo:audit']">审核
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:serviceAppointmentInfo:remove']">删除
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

    <!-- 添加或修改服务预约对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="serviceAppointmentInfoRef" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="服务类型" prop="serviceType">
          <el-select v-model="form.serviceType" placeholder="请选择服务类型">
            <el-option
                v-for="dict in healt_service_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactMethod">
          <el-input v-model="form.contactMethod" placeholder="请输入联系方式"/>
        </el-form-item>
        <el-form-item label="预约标题" prop="appointmentTitle">
          <el-input v-model="form.appointmentTitle" placeholder="请输入预约标题"/>
        </el-form-item>
        <el-form-item label="预约原因" prop="appointmentReason">
          <el-input v-model="form.appointmentReason" placeholder="请输入预约原因"/>
        </el-form-item>
        <el-form-item label="预约日期" prop="appointmentTime">
          <el-date-picker clearable
                          v-model="form.appointmentTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择预约日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="具体时间" prop="dateType">
          <el-select v-model="form.dateType" placeholder="请选择具体时间">
            <el-option
                v-for="dict in health_date_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="状态" prop="status">-->
        <!--          <el-radio-group v-model="form.status">-->
        <!--            <el-radio-->
        <!--                v-for="dict in health_appointment_status"-->
        <!--                :key="dict.value"-->
        <!--                :value="dict.value"-->
        <!--            >{{ dict.label }}-->
        <!--            </el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <el-form-item label="预约医生" prop="appointmentUserId">
          <el-select
              v-model="form.appointmentUserId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetUserList"
              :loading="userLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.userName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="服务时间" prop="serviceTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.serviceTime"-->
        <!--                          type="date"-->
        <!--                          value-format="YYYY-MM-DD"-->
        <!--                          placeholder="请选择服务时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="附件" prop="appendix">-->
        <!--          <file-upload v-model="form.appendix"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="所属用户" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入所属用户"/>-->
        <!--        </el-form-item>-->
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
    <!-- 审核服务预约对话框 -->
    <el-dialog :title="title" v-model="openAudit" width="500px" append-to-body>
      <el-form ref="serviceAppointmentInfoAuditRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in health_appointment_status"
                :key="dict.value"
                :value="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="服务时间" prop="serviceTime">
          <el-date-picker clearable
                          v-model="form.serviceTime"
                          type="datetime"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          placeholder="请选择服务时间">
          </el-date-picker>
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
          <el-button type="primary" @click="submitAuditForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ServiceAppointmentInfo">
import {
  listServiceAppointmentInfo,
  getServiceAppointmentInfo,
  delServiceAppointmentInfo,
  addServiceAppointmentInfo,
  updateServiceAppointmentInfo, auditServiceAppointmentInfo
} from "@/api/manage/serviceAppointmentInfo";
import {listResidentInfo} from "@/api/manage/residentInfo.js";
import {allocatedUserListAll} from "@/api/system/role.js";

const {proxy} = getCurrentInstance();
const {
  health_date_type,
  healt_service_type,
  health_appointment_status
} = proxy.useDict('health_date_type', 'healt_service_type', 'health_appointment_status');

const serviceAppointmentInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeAppointmentTime = ref([]);
const daterangeCreateTime = ref([]);


const openAudit = ref(false);
const handleAppointmentAudit = (row) => {
  reset();
  const _id = row.id || ids.value
  getServiceAppointmentInfo(_id).then(response => {
    form.value = response.data;
    openAudit.value = true;
    title.value = "审核服务预约";
  });
}

const submitAuditForm = () => {
  proxy.$refs["serviceAppointmentInfoAuditRef"].validate(valid => {
    if (!valid) return;
    auditServiceAppointmentInfo(form.value).then(res => {
      openAudit.value = false;
      proxy.$modal.msgSuccess("审核成功");
      getList();
    })
  })
}

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/serviceAppointmentInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    residentId: null,
    serviceType: null,
    appointmentTitle: null,
    appointmentTime: null,
    dateType: null,
    status: null,
    appointmentUserId: null,
    userId: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    residentId: [
      {required: true, message: "居民不能为空", trigger: "blur"}
    ],
    serviceType: [
      {required: true, message: "服务类型不能为空", trigger: "change"}
    ],
    contactMethod: [
      {required: true, message: "联系方式不能为空", trigger: "blur"}
    ],
    appointmentTitle: [
      {required: true, message: "预约标题不能为空", trigger: "blur"}
    ],
    appointmentReason: [
      {required: true, message: "预约原因不能为空", trigger: "blur"}
    ],
    appointmentTime: [
      {required: true, message: "预约日期不能为空", trigger: "blur"}
    ],
    dateType: [
      {required: true, message: "具体时间不能为空", trigger: "change"}
    ],
    status: [
      {required: true, message: "状态不能为空", trigger: "change"}
    ],
    appointmentUserId: [
      {required: true, message: "预约医生不能为空", trigger: "blur"}
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
    {key: 2, label: '服务类型', visible: true},
    {key: 3, label: '联系方式', visible: true},
    {key: 4, label: '预约标题', visible: true},
    {key: 5, label: '预约原因', visible: true},
    {key: 6, label: '预约日期', visible: true},
    {key: 7, label: '具体时间', visible: true},
    {key: 8, label: '状态', visible: true},
    {key: 9, label: '预约医生', visible: true},
    {key: 10, label: '服务时间', visible: true},
    {key: 11, label: '附件', visible: true},
    {key: 12, label: '所属用户', visible: true},
    {key: 13, label: '创建人', visible: false},
    {key: 14, label: '创建时间', visible: true},
    {key: 15, label: '更新人', visible: false},
    {key: 16, label: '更新时间', visible: false},
    {key: 17, label: '备注', visible: true},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询服务预约列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeAppointmentTime && '' != daterangeAppointmentTime) {
    queryParams.value.params["beginAppointmentTime"] = daterangeAppointmentTime.value[0];
    queryParams.value.params["endAppointmentTime"] = daterangeAppointmentTime.value[1];
  }
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listServiceAppointmentInfo(queryParams.value).then(response => {
    serviceAppointmentInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  openAudit.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    residentId: null,
    serviceType: null,
    contactMethod: null,
    appointmentTitle: null,
    appointmentReason: null,
    appointmentTime: null,
    dateType: null,
    status: null,
    appointmentUserId: null,
    serviceTime: null,
    appendix: null,
    userId: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("serviceAppointmentInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeAppointmentTime.value = [];
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
  title.value = "添加服务预约";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getServiceAppointmentInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改服务预约";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["serviceAppointmentInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateServiceAppointmentInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addServiceAppointmentInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除服务预约编号为"' + _ids + '"的数据项？').then(function () {
    return delServiceAppointmentInfo(_ids);
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
  }, `serviceAppointmentInfo_${new Date().getTime()}.xlsx`)
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


const userList = ref([]);
const userLoading = ref(false);
const userQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 100
});
const getUserList = () => {
  userLoading.value = true;
  allocatedUserListAll(userQueryParams).then(response => {
    userList.value = response.rows;
    userLoading.value = false;
  });
};
const remoteGetUserList = (query) => {
  userQueryParams.name = query;
  getUserList();
}

getUserList()

getResidentList()

getList();
</script>
