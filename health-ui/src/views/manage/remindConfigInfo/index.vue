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
                    <el-input
                        v-model="queryParams.residentId"
                        placeholder="请输入居民"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="提醒类型" prop="remindType">
                    <el-select v-model="queryParams.remindType" style="width: 200px" placeholder="请选择提醒类型" clearable>
                      <el-option
                          v-for="dict in health_remind_type"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="提醒标题" prop="remindTitle">
                    <el-input
                        v-model="queryParams.remindTitle"
                        placeholder="请输入提醒标题"
                        clearable
                        @keyup.enter="handleQuery"
                    />
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
            v-hasPermi="['manage:remindConfigInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:remindConfigInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:remindConfigInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:remindConfigInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="remindConfigInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="居民" align="center" prop="residentId" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒类型" align="center" prop="remindType" v-if="columns[2].visible">
                <template #default="scope">
                      <dict-tag :options="health_remind_type" :value="scope.row.remindType"/>
                </template>
              </el-table-column>
                <el-table-column label="提醒标题" align="center" prop="remindTitle" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒内容" align="center" prop="remindContent" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒时间" align="center" prop="remindTime" width="180" v-if="columns[5].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.remindTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="开始时间" align="center" prop="startTime" width="180" v-if="columns[6].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="结束时间" align="center" prop="endTime" width="180" v-if="columns[7].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="每天提醒次数" align="center" prop="remindFrequency" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="提醒间隔分钟" align="center" prop="remindInterval" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="上次提醒时间" align="center" prop="lastRemindTime" width="180" v-if="columns[10].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.lastRemindTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="下次提醒时间" align="center" prop="nextRemindTime" width="180" v-if="columns[11].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.nextRemindTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="所属用户" align="center" prop="userId" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[15].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[16].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="备注" align="center" prop="remark" v-if="columns[17].visible" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:remindConfigInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:remindConfigInfo:remove']">删除</el-button>
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

    <!-- 添加或修改提醒配置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="remindConfigInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="居民" prop="residentId">
                          <el-input v-model="form.residentId" placeholder="请输入居民" />
                        </el-form-item>
                        <el-form-item label="提醒类型" prop="remindType">
                          <el-select v-model="form.remindType" placeholder="请选择提醒类型">
                            <el-option
                                v-for="dict in health_remind_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="提醒标题" prop="remindTitle">
                          <el-input v-model="form.remindTitle" placeholder="请输入提醒标题" />
                        </el-form-item>
                        <el-form-item label="提醒内容">
                          <editor v-model="form.remindContent" :min-height="192"/>
                        </el-form-item>
                        <el-form-item label="提醒时间" prop="remindTime">
                          <el-date-picker clearable
                                          v-model="form.remindTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择提醒时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="开始时间" prop="startTime">
                          <el-date-picker clearable
                                          v-model="form.startTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择开始时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间" prop="endTime">
                          <el-date-picker clearable
                                          v-model="form.endTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择结束时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="每天提醒次数" prop="remindFrequency">
                          <el-input v-model="form.remindFrequency" placeholder="请输入每天提醒次数" />
                        </el-form-item>
                        <el-form-item label="提醒间隔分钟" prop="remindInterval">
                          <el-input v-model="form.remindInterval" placeholder="请输入提醒间隔分钟" />
                        </el-form-item>
                        <el-form-item label="上次提醒时间" prop="lastRemindTime">
                          <el-date-picker clearable
                                          v-model="form.lastRemindTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择上次提醒时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="下次提醒时间" prop="nextRemindTime">
                          <el-date-picker clearable
                                          v-model="form.nextRemindTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择下次提醒时间">
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

<script setup name="RemindConfigInfo">
  import { listRemindConfigInfo, getRemindConfigInfo, delRemindConfigInfo, addRemindConfigInfo, updateRemindConfigInfo } from "@/api/manage/remindConfigInfo";

  const { proxy } = getCurrentInstance();
      const { health_remind_type } = proxy.useDict('health_remind_type');

  const remindConfigInfoList = ref([]);
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
    exportUrl: 'manage/remindConfigInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    residentId: null,
                    remindType: null,
                    remindTitle: null,
                    userId: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    residentId: [
                { required: true, message: "居民不能为空", trigger: "blur" }
              ],
                    remindType: [
                { required: true, message: "提醒类型不能为空", trigger: "change" }
              ],
                    remindTitle: [
                { required: true, message: "提醒标题不能为空", trigger: "blur" }
              ],
                    remindContent: [
                { required: true, message: "提醒内容不能为空", trigger: "blur" }
              ],
                    remindTime: [
                { required: true, message: "提醒时间不能为空", trigger: "blur" }
              ],
                    startTime: [
                { required: true, message: "开始时间不能为空", trigger: "blur" }
              ],
                    remindFrequency: [
                { required: true, message: "每天提醒次数不能为空", trigger: "blur" }
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
                { key: 1, label: '居民', visible: true },
                { key: 2, label: '提醒类型', visible: true },
                { key: 3, label: '提醒标题', visible: true },
                { key: 4, label: '提醒内容', visible: true },
                { key: 5, label: '提醒时间', visible: true },
                { key: 6, label: '开始时间', visible: true },
                { key: 7, label: '结束时间', visible: true },
                { key: 8, label: '每天提醒次数', visible: true },
                { key: 9, label: '提醒间隔分钟', visible: true },
                { key: 10, label: '上次提醒时间', visible: true },
                { key: 11, label: '下次提醒时间', visible: true },
                { key: 12, label: '所属用户', visible: true },
                { key: 13, label: '创建人', visible: true },
                { key: 14, label: '创建时间', visible: true },
                { key: 15, label: '更新人', visible: true },
                { key: 16, label: '更新时间', visible: true },
                { key: 17, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询提醒配置列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listRemindConfigInfo(queryParams.value).then(response => {
            remindConfigInfoList.value = response.rows;
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
                    remindType: null,
                    remindTitle: null,
                    remindContent: null,
                    remindTime: null,
                    startTime: null,
                    endTime: null,
                    remindFrequency: null,
                    remindInterval: null,
                    lastRemindTime: null,
                    nextRemindTime: null,
                    userId: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("remindConfigInfoRef");
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
    title.value = "添加提醒配置";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getRemindConfigInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改提醒配置";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["remindConfigInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateRemindConfigInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addRemindConfigInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除提醒配置编号为"' + _ids + '"的数据项？').then(function() {
      return delRemindConfigInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `remindConfigInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
