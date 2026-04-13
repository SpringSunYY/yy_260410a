import request from '@/utils/request'

// 查询健康档案列表
export function listHealthRecordInfo(query) {
  return request({
    url: '/manage/healthRecordInfo/list',
    method: 'get',
    params: query
  })
}

// 查询健康档案详细
export function getHealthRecordInfo(id) {
  return request({
    url: '/manage/healthRecordInfo/' + id,
    method: 'get'
  })
}

// 新增健康档案
export function addHealthRecordInfo(data) {
  return request({
    url: '/manage/healthRecordInfo',
    method: 'post',
    data: data
  })
}

// 修改健康档案
export function updateHealthRecordInfo(data) {
  return request({
    url: '/manage/healthRecordInfo',
    method: 'put',
    data: data
  })
}

// 删除健康档案
export function delHealthRecordInfo(id) {
  return request({
    url: '/manage/healthRecordInfo/' + id,
    method: 'delete'
  })
}
