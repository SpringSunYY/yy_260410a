import request from '@/utils/request'

// 查询诊疗记录列表
export function listTreatmentInfo(query) {
  return request({
    url: '/manage/treatmentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询诊疗记录详细
export function getTreatmentInfo(id) {
  return request({
    url: '/manage/treatmentInfo/' + id,
    method: 'get'
  })
}

// 新增诊疗记录
export function addTreatmentInfo(data) {
  return request({
    url: '/manage/treatmentInfo',
    method: 'post',
    data: data
  })
}

// 修改诊疗记录
export function updateTreatmentInfo(data) {
  return request({
    url: '/manage/treatmentInfo',
    method: 'put',
    data: data
  })
}

// 删除诊疗记录
export function delTreatmentInfo(id) {
  return request({
    url: '/manage/treatmentInfo/' + id,
    method: 'delete'
  })
}
