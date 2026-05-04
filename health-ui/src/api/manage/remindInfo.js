import request from '@/utils/request'

// 查询提醒记录列表
export function listRemindInfo(query) {
  return request({
    url: '/manage/remindInfo/list',
    method: 'get',
    params: query
  })
}

// 查询提醒记录详细
export function getRemindInfo(id) {
  return request({
    url: '/manage/remindInfo/' + id,
    method: 'get'
  })
}

// 新增提醒记录
export function addRemindInfo(data) {
  return request({
    url: '/manage/remindInfo',
    method: 'post',
    data: data
  })
}

// 修改提醒记录
export function updateRemindInfo(data) {
  return request({
    url: '/manage/remindInfo',
    method: 'put',
    data: data
  })
}

// 删除提醒记录
export function delRemindInfo(id) {
  return request({
    url: '/manage/remindInfo/' + id,
    method: 'delete'
  })
}
