<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="居民名称" prop="residentName">
        <el-input
            v-model="queryParams.residentName"
            placeholder="请输入居民名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" style="width: 200px" placeholder="请选择性别" clearable>
          <el-option
              v-for="dict in sys_user_sex"
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
            v-hasPermi="['manage:residentInfo:add']"
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
            v-hasPermi="['manage:residentInfo:edit']"
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
            v-hasPermi="['manage:residentInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:residentInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="residentInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="居民名称" align="center" prop="residentName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="性别" align="center" prop="gender" v-if="columns[2].visible">
        <template #default="scope">
          <dict-tag :options="sys_user_sex" :value="scope.row.gender"/>
        </template>
      </el-table-column>
      <el-table-column label="出生日期" align="center" prop="birthDateTime" width="180" v-if="columns[3].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.birthDateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份证号" align="center" prop="idCard" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="联系电话" align="center" prop="contactPhone" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="居住地址" align="center" prop="address" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="紧急联系人" align="center" prop="emergencyContact" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="紧急联系电话" align="center" prop="emergencyPhone" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="附件" align="center" prop="appendix" width="100" v-if="columns[9].visible">
        <template #default="scope">
          <file-view :file-url="scope.row.appendix"/>
        </template>
      </el-table-column>
      <el-table-column label="所属用户" align="center" prop="userName" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[12].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:residentInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:residentInfo:remove']">删除
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

    <!-- 添加或修改居民信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="residentInfoRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="居民名称" prop="residentName">
          <el-input v-model="form.residentName" placeholder="请输入居民名称"/>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option
                v-for="dict in sys_user_sex"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDateTime">
          <el-date-picker clearable
                          v-model="form.birthDateTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="居住地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入居住地址"/>
        </el-form-item>
        <el-form-item label="紧急联系人" prop="emergencyContact">
          <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人"/>
        </el-form-item>
        <el-form-item label="紧急联系电话" prop="emergencyPhone">
          <el-input v-model="form.emergencyPhone" placeholder="请输入紧急联系电话"/>
        </el-form-item>
        <el-form-item label="附件" prop="appendix">
          <file-upload v-model="form.appendix"/>
        </el-form-item>
        <el-form-item label="所属用户" prop="userId">
          <el-select
              v-model="form.userId"
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

<script setup name="ResidentInfo">
import {
  listResidentInfo,
  getResidentInfo,
  delResidentInfo,
  addResidentInfo,
  updateResidentInfo
} from "@/api/manage/residentInfo";
import {allocatedUserListAll} from "@/api/system/role.js";

const {proxy} = getCurrentInstance();
const {sys_user_sex} = proxy.useDict('sys_user_sex');

const residentInfoList = ref([]);
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
  exportUrl: 'manage/residentInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    residentName: null,
    gender: null,
    userId: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    residentName: [
      {required: true, message: "居民名称不能为空", trigger: "blur"}
    ],
    gender: [
      {required: true, message: "性别不能为空", trigger: "change"}
    ],
    birthDateTime: [
      {required: true, message: "出生日期不能为空", trigger: "blur"}
    ],
    contactPhone: [
      {required: true, message: "联系电话不能为空", trigger: "blur"},
      {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}],
    idCard: [
      {required: true, message: "身份证号不能为空", trigger: "blur"},
      {
        pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9xX]$/,
        message: "请输入正确的身份证号",
        trigger: "blur"
      }
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
    {key: 1, label: '居民名称', visible: true},
    {key: 2, label: '性别', visible: true},
    {key: 3, label: '出生日期', visible: true},
    {key: 4, label: '身份证号', visible: true},
    {key: 5, label: '联系电话', visible: true},
    {key: 6, label: '居住地址', visible: true},
    {key: 7, label: '紧急联系人', visible: true},
    {key: 8, label: '紧急联系电话', visible: true},
    {key: 9, label: '附件', visible: true},
    {key: 10, label: '所属用户', visible: true},
    {key: 11, label: '创建人', visible: true},
    {key: 12, label: '创建时间', visible: true},
    {key: 13, label: '更新人', visible: false},
    {key: 14, label: '更新时间', visible: false},
    {key: 15, label: '备注', visible: true},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询居民信息列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listResidentInfo(queryParams.value).then(response => {
    residentInfoList.value = response.rows;
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
    residentName: null,
    gender: null,
    birthDateTime: null,
    idCard: null,
    contactPhone: null,
    address: null,
    emergencyContact: null,
    emergencyPhone: null,
    appendix: null,
    userId: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("residentInfoRef");
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
  title.value = "添加居民信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getResidentInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改居民信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["residentInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateResidentInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addResidentInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除居民信息编号为"' + _ids + '"的数据项？').then(function () {
    return delResidentInfo(_ids);
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
  }, `residentInfo_${new Date().getTime()}.xlsx`)
}

const userList = ref([]);
const userLoading = ref(false);
const userQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 2
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
getList();
</script>
