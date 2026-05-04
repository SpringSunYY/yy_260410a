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
                  <el-form-item label="配置" prop="reminderId">
                    <el-input
                        v-model="queryParams.reminderId"
                        placeholder="请输入配置"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="居民" prop="residentId">
                    <el-input
                        v-model="queryParams.residentId"
                        placeholder="请输入居民"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="提醒类型" prop="reminderType">
                    <el-select v-model="queryParams.reminderType" style="width: 200px" placeholder="请选择提醒类型" clearable>
                      <el-option
                          v-for="dict in health_remind_type"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="提醒标题" prop="reminderTitle">
                    <el-input
                        v-model="queryParams.reminderTitle"
                        placeholder="请输入提醒标题"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="已读状态" prop="readStatus">
                    <el-select v-model="queryParams.readStatus" style="width: 200px" placeholder="请选择已读状态" clearable>
                      <el-option
                          v-for="dict in health_read_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="已读时间" style="width: 308px">
                    <el-date-picker
                        v-model="daterangeReadTime"
                        value-format="YYYY-MM-DD"
                        type="daterange"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                    ></el-date-picker>
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
            v-hasPermi="['manage:remindInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:remindInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:remindInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:remindInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="remindInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="配置" align="center" prop="reminderId" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="居民" align="center" prop="residentId" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒类型" align="center" prop="reminderType" v-if="columns[3].visible">
                <template #default="scope">
                      <dict-tag :options="health_remind_type" :value="scope.row.reminderType"/>
                </template>
              </el-table-column>
                <el-table-column label="提醒标题" align="center" prop="reminderTitle" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒内容" align="center" prop="reminderContent" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒时间" align="center" prop="remindTime" width="180" v-if="columns[6].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.remindTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="已读状态" align="center" prop="readStatus" v-if="columns[7].visible">
                <template #default="scope">
                      <dict-tag :options="health_read_status" :value="scope.row.readStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="已读时间" align="center" prop="readTime" width="180" v-if="columns[8].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.readTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="所属用户" align="center" prop="userId" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[11].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[13].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="备注" align="center" prop="remark" v-if="columns[14].visible" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:remindInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:remindInfo:remove']">删除</el-button>
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

    <!-- 添加或修改提醒记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="remindInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="配置" prop="reminderId">
                          <el-input v-model="form.reminderId" placeholder="请输入配置" />
                        </el-form-item>
                        <el-form-item label="居民" prop="residentId">
                          <el-input v-model="form.residentId" placeholder="请输入居民" />
                        </el-form-item>
                        <el-form-item label="提醒类型" prop="reminderType">
                          <el-select v-model="form.reminderType" placeholder="请选择提醒类型">
                            <el-option
                                v-for="dict in health_remind_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="提醒标题" prop="reminderTitle">
                          <el-input v-model="form.reminderTitle" placeholder="请输入提醒标题" />
                        </el-form-item>
                        <el-form-item label="提醒内容">
                          <editor v-model="form.reminderContent" :min-height="192"/>
                        </el-form-item>
                        <el-form-item label="提醒时间" prop="remindTime">
                          <el-date-picker clearable
                                          v-model="form.remindTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择提醒时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="已读状态" prop="readStatus">
                          <el-select v-model="form.readStatus" placeholder="请选择已读状态">
                            <el-option
                                v-for="dict in health_read_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="已读时间" prop="readTime">
                          <el-date-picker clearable
                                          v-model="form.readTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择已读时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="所属用户" prop="userId">
                          <el-input v-model="form.userId" placeholder="请输入所属用户" />
                        </el-form-item>
                        <el-form-item label="备注" prop="remark">
                          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="RemindInfo">
  import { listRemindInfo, getRemindInfo, delRemindInfo, addRemindInfo, updateRemindInfo } from "@/api/manage/remindInfo";

  const { proxy } = getCurrentInstance();
      const { health_remind_type, health_read_status } = proxy.useDict('health_remind_type', 'health_read_status');

  const remindInfoList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
          const daterangeReadTime = ref([]);
          const daterangeCreateTime = ref([]);

  const data = reactive({
    form: {},
    // 导出地址
    exportUrl: 'manage/remindInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    reminderId: null,
                    residentId: null,
                    reminderType: null,
                    reminderTitle: null,
                    readStatus: null,
                    readTime: null,
                    userId: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    reminderId: [
                { required: true, message: "配置不能为空", trigger: "blur" }
              ],
                    residentId: [
                { required: true, message: "居民不能为空", trigger: "blur" }
              ],
                    reminderType: [
                { required: true, message: "提醒类型不能为空", trigger: "change" }
              ],
                    reminderTitle: [
                { required: true, message: "提醒标题不能为空", trigger: "blur" }
              ],
                    reminderContent: [
                { required: true, message: "提醒内容不能为空", trigger: "blur" }
              ],
                    readStatus: [
                { required: true, message: "已读状态不能为空", trigger: "change" }
              ],
                    userId: [
                { required: true, message: "所属用户不能为空", trigger: "blur" }
              ],
                    createBy: [
                { required: true, message: "创建人不能为空", trigger: "blur" }
              ],
                    createTime: [
                { required: true, message: "创建时间不能为空", trigger: "blur" }
              ],
    },
    //表格展示列
    columns: [
              { key: 0, label: '编号', visible: true },
                { key: 1, label: '配置', visible: true },
                { key: 2, label: '居民', visible: true },
                { key: 3, label: '提醒类型', visible: true },
                { key: 4, label: '提醒标题', visible: true },
                { key: 5, label: '提醒内容', visible: true },
                { key: 6, label: '提醒时间', visible: true },
                { key: 7, label: '已读状态', visible: true },
                { key: 8, label: '已读时间', visible: true },
                { key: 9, label: '所属用户', visible: true },
                { key: 10, label: '创建人', visible: true },
                { key: 11, label: '创建时间', visible: true },
                { key: 12, label: '更新人', visible: true },
                { key: 13, label: '更新时间', visible: true },
                { key: 14, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询提醒记录列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeReadTime && '' != daterangeReadTime) {
              queryParams.value.params["beginReadTime"] = daterangeReadTime.value[0];
              queryParams.value.params["endReadTime"] = daterangeReadTime.value[1];
            }
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listRemindInfo(queryParams.value).then(response => {
            remindInfoList.value = response.rows;
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
                    reminderId: null,
                    residentId: null,
                    reminderType: null,
                    reminderTitle: null,
                    reminderContent: null,
                    remindTime: null,
                    readStatus: null,
                    readTime: null,
                    userId: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("remindInfoRef");
  }

  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
  }

  /** 重置按钮操作 */
  function resetQuery() {
            daterangeReadTime.value = [];
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
    title.value = "添加提醒记录";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getRemindInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改提醒记录";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["remindInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateRemindInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addRemindInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除提醒记录编号为"' + _ids + '"的数据项？').then(function() {
      return delRemindInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `remindInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
