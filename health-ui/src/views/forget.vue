<template>
  <div class="forget">
    <el-form ref="forgetRef" :model="forgetForm" :rules="forgetRules" class="forget-form">
      <h3 class="title">{{ title }}</h3>
      <el-form-item prop="username">
        <el-input
          v-model="forgetForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
          <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="forgetForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="新密码"
          @keyup.enter="handleForget"
        >
          <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="forgetForm.confirmPassword"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter="handleForget"
        >
          <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          size="large"
          v-model="forgetForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter="handleForget"
        >
          <template #prefix><svg-icon icon-class="validCode" class="el-input__icon input-icon" /></template>
        </el-input>
        <div class="forget-code">
          <el-button
            :disabled="countdown > 0"
            size="large"
            type="primary"
            @click="sendCode"
            style="width: 100%; height: 40px;"
          >
            <span v-if="countdown === 0">发送验证码</span>
            <span v-else>{{ countdown }}s</span>
          </el-button>
        </div>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          style="width:100%;"
          @click.prevent="handleForget"
        >
          <span v-if="!loading">重置密码</span>
          <span v-else>重置中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">返回登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-forget-footer">
      <span>{{ footerContent }}</span>
    </div>
  </div>
</template>

<script setup>
import { forgetPassword, sendVerifyCode } from "@/api/login"
import defaultSettings from '@/settings'

const title = import.meta.env.VITE_APP_TITLE
const footerContent = defaultSettings.footerContent
const router = useRouter()
const { proxy } = getCurrentInstance()

const forgetForm = ref({
  username: "",
  password: "",
  confirmPassword: "",
  code: ""
})

const equalToPassword = (rule, value, callback) => {
  if (forgetForm.value.password !== value) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const forgetRules = {
  username: [
    { required: true, trigger: "blur", message: "请输入您的账号" },
    { min: 2, max: 20, message: "用户账号长度必须介于 2 和 20 之间", trigger: "blur" }
  ],
  password: [
    { required: true, trigger: "blur", message: "请输入您的新密码" },
    { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" },
    { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\ |", trigger: "blur" }
  ],
  confirmPassword: [
    { required: true, trigger: "blur", message: "请再次输入您的新密码" },
    { required: true, validator: equalToPassword, trigger: "blur" }
  ],
  code: [{ required: true, trigger: "blur", message: "请输入验证码" }]
}

const loading = ref(false)
const countdown = ref(0)
let countdownTimer = null

function sendCode() {
  if (!forgetForm.value.username) {
    proxy.$message.error("请先输入账号")
    return
  }
  sendVerifyCode(forgetForm.value.username).then(res => {
    proxy.$message.success("验证码已发送，请查看控制台")
    countdown.value = 60
    countdownTimer = setInterval(() => {
      countdown.value--
      if (countdown.value <= 0) {
        clearInterval(countdownTimer)
      }
    }, 1000)
  }).catch(() => {})
}

function handleForget() {
  proxy.$refs.forgetRef.validate(valid => {
    if (valid) {
      loading.value = true
      forgetPassword(forgetForm.value).then(res => {
        proxy.$message.success("密码重置成功，请使用新密码登录")
        router.push("/login")
      }).catch(() => {
        loading.value = false
      })
    }
  })
}

onUnmounted(() => {
  if (countdownTimer) {
    clearInterval(countdownTimer)
  }
})
</script>

<style lang='scss' scoped>
.forget {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.forget-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 40px;
    input {
      height: 40px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}
.forget-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.forget-code {
  width: 34%;
  height: 40px;
  float: right;
  margin-left: 2%;
}
.el-forget-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
