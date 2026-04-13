<template>
  <div class="app-container home">
    <h1 class="page-title">{{ title}}</h1>

    <div class="shortcut-grid">
      <div class="shortcut-card" v-has-permi="['manage:healthRecordInfo:list']"
           @click="goShortcut('HealthRecordInfo')">
        <div class="card-icon health-record-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M896 128H640c-17.7 0-32 14.3-32 32v128h-64c-17.7 0-32 14.3-32 32v640c0 17.7 14.3 32 32 32h384c17.7 0 32-14.3 32-32V544h64c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32zM608 832H416V736h192v96zm128-288H544v-64h192v64z"/>
            <path
                d="M256 192c-35.3 0-64 28.7-64 64v64c0 35.3 28.7 64 64 64s64-28.7 64-64v-64c0-35.3-28.7-64-64-64zm0 96c-17.7 0-32-14.3-32-32v-32c0-17.7 14.3-32 32-32s32 14.3 32 32v32c0 17.7-14.3 32-32 32z"/>
          </svg>
        </div>
        <div class="card-label">健康档案</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:healthHistoryInfo:list']"
           @click="goShortcut('HealthHistoryInfo')">
        <div class="card-icon health-history-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M896 192H128c-35.3 0-64 28.7-64 64v512c0 35.3 28.7 64 64 64h768c35.3 0 64-28.7 64-64V256c0-35.3-28.7-64-64-64zM832 640H192V320h640v320zm64-128c0 35.3-28.7 64-64 64s-64-28.7-64-64 28.7-64 64-64 64 28.7 64 64z"/>
            <path
                d="M320 416h64v192h-64zM416 384h-64c-17.7 0-32 14.3-32 32v64c0 17.7 14.3 32 32 32h64c17.7 0 32-14.3 32-32v-64c0-17.7-14.3-32-32-32zm0 96h-64v-32h64v32z"/>
          </svg>
        </div>
        <div class="card-label">健康记录</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:residentInfo:list']"
           @click="goShortcut('ResidentInfo')">
        <div class="card-icon resident-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M512 192c-79.5 0-144 64.5-144 144s64.5 144 144 144 144-64.5 144-144-64.5-144-144-144zm256 416h-128v-64c0-35.3-28.7-64-64-64s-64 28.7-64 64v64h-128c-35.3 0-64 28.7-64 64v192c0 35.3 28.7 64 64 64h384c35.3 0 64-28.7 64-64V608c0-35.3-28.7-64-64-64z"/>
          </svg>
        </div>
        <div class="card-label">居民信息</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:hospitalizationInfo:list']"
           @click="goShortcut('HospitalizationInfo')">
        <div class="card-icon hospital-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M896 128H640c-17.7 0-32 14.3-32 32v64H416v-64c0-17.7-14.3-32-32-32H128c-17.7 0-32 14.3-32 32v256c0 17.7 14.3 32 32 32h224v128H256c-17.7 0-32 14.3-32 32v192c0 17.7 14.3 32 32 32h512c17.7 0 32-14.3 32-32V672c0-17.7-14.3-32-32-32H704V384h96v256c0 17.7 14.3 32 32 32h64c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32zM480 832H288V704h192v128z"/>
          </svg>
        </div>
        <div class="card-label">住院记录</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:serviceAppointmentInfo:list']"
           @click="goShortcut('ServiceAppointmentInfo')">
        <div class="card-icon appointment-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M832 192H704c-17.7 0-32 14.3-32 32v64c0 17.7 14.3 32 32 32h32v64c0 17.7 14.3 32 32 32h64c17.7 0 32-14.3 32-32v-64h32c17.7 0 32-14.3 32-32v-64c0-17.7-14.3-32-32-32zm32 96h-64v-32h64v32z"/>
            <path
                d="M896 128H640c-35.3 0-64 28.7-64 64v64H192v64h64v64H192v64h64v128c0 35.3 28.7 64 64 64h384c35.3 0 64-28.7 64-64V544h64v-64h-64v-64h256V192c0-35.3-28.7-64-64-64zM704 768H320V320h384v448z"/>
          </svg>
        </div>
        <div class="card-label">服务预约</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:treatmentInfo:list']"
           @click="goShortcut('TreatmentInfo')">
        <div class="card-icon treatment-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path
                d="M864 192H672v-64c0-35.3-28.7-64-64-64s-64 28.7-64 64v64H416v-64c0-35.3-28.7-64-64-64s-64 28.7-64 64v64H160c-35.3 0-64 28.7-64 64v512c0 35.3 28.7 64 64 64h704c35.3 0 64-28.7 64-64V256c0-35.3-28.7-64-64-64zM544 192c-35.3 0-64 28.7-64 64v192c0 35.3 28.7 64 64 64s64-28.7 64-64V256c0-35.3-28.7-64-64-64zm64 320c0 17.7-14.3 32-32 32s-32-14.3-32-32v-32c0-17.7 14.3-32 32-32s32 14.3 32 32v32zm192 0c0 17.7-14.3 32-32 32s-32-14.3-32-32v-32c0-17.7 14.3-32 32-32s32 14.3 32 32v32zm-192 0c0 17.7-14.3 32-32 32s-32-14.3-32-32V352c0-17.7 14.3-32 32-32s32 14.3 32 32v160zm256 0c0 17.7-14.3 32-32 32s-32-14.3-32-32V352c0-17.7 14.3-32 32-32s32 14.3 32 32v160z"/>
          </svg>
        </div>
        <div class="card-label">诊疗记录</div>
      </div>
    </div>

  </div>
</template>

<script setup>
const title = import.meta.env.VITE_APP_TITLE
import {useRouter} from "vue-router"
const router = useRouter()
const goShortcut = (name) => {
  router.push({name})
}
</script>

<style scoped lang="scss">
.home {
  padding: 32px;
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
  background: #f5f7fa;
  min-height: 100vh;
  box-sizing: border-box;

  .page-title {
    margin: 0 auto;
    text-align: center;
    font-size: 48px;
    color: #1ab394;
    font-weight: bold;
  }

  .shortcut-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
    max-width: 900px;
    margin: 100px auto 100px;

    .shortcut-card {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: #fff;
      border-radius: 12px;
      padding: 32px 16px;
      cursor: pointer;
      transition: transform 0.25s ease, box-shadow 0.25s ease;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
      border: 1px solid rgba(0, 0, 0, 0.06);

      &:hover {
        transform: translateY(-6px);
        box-shadow: 0 12px 32px rgba(0, 0, 0, 0.14);

        .card-icon {
          transform: scale(1.1);
        }
      }

      &:active {
        transform: translateY(-2px);
      }

      .card-icon {
        width: 72px;
        height: 72px;
        border-radius: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 16px;
        transition: transform 0.25s ease;

        &.health-record-icon {
          background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        }

        &.health-history-icon {
          background: linear-gradient(135deg, #00c6ff 0%, #0072ff 100%);
        }

        &.resident-icon {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        &.hospital-icon {
          background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        &.appointment-icon {
          background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        &.treatment-icon {
          background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        .icon {
          color: #fff;
        }
      }

      .card-label {
        font-size: 15px;
        font-weight: 600;
        color: #2c3e50;
        text-align: center;
      }
    }
  }

  .info-section {
    max-width: 1100px;
    margin: 0 auto;

    blockquote {
      padding: 16px 24px;
      margin: 0;
      font-size: 14px;
      border-left: 4px solid #667eea;
      background: #fff;
      border-radius: 0 8px 8px 0;
      box-shadow: 0 1px 8px rgba(0, 0, 0, 0.06);
      color: #444;

      ol {
        display: block;
        list-style-type: decimal;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0;
        margin-inline-end: 0;
        padding-inline-start: 24px;

        li {
          padding: 4px 0;
          color: #555;
          line-height: 1.6;
        }
      }
    }
  }

  @media (max-width: 900px) {
    .shortcut-grid {
      grid-template-columns: repeat(3, 1fr);
    }
  }

  @media (max-width: 600px) {
    .shortcut-grid {
      grid-template-columns: repeat(2, 1fr);
    }
  }
}
</style>

