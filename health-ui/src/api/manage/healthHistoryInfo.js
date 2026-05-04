import request from '@/utils/request'

// 查询健康记录列表
export function listHealthHistoryInfo(query) {
  return request({
    url: '/manage/healthHistoryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询健康记录详细
export function getHealthHistoryInfo(id) {
  return request({
    url: '/manage/healthHistoryInfo/' + id,
    method: 'get'
  })
}

// 新增健康记录
export function addHealthHistoryInfo(data) {
  return request({
    url: '/manage/healthHistoryInfo',
    method: 'post',
    data: data
  })
}

// 修改健康记录
export function updateHealthHistoryInfo(data) {
  return request({
    url: '/manage/healthHistoryInfo',
    method: 'put',
    data: data
  })
}

// 删除健康记录
export function delHealthHistoryInfo(id) {
  return request({
    url: '/manage/healthHistoryInfo/' + id,
    method: 'delete'
  })
}

//统计
export function statisticsHealthHistoryInfo(query) {
  return request({
    url: '/manage/healthHistoryInfo/statistics',
    method: 'get',
    params: query
  })
}
