import request from '@/utils/request'

// 查询提醒配置列表
export function listRemindConfigInfo(query) {
  return request({
    url: '/manage/remindConfigInfo/list',
    method: 'get',
    params: query
  })
}

// 查询提醒配置详细
export function getRemindConfigInfo(id) {
  return request({
    url: '/manage/remindConfigInfo/' + id,
    method: 'get'
  })
}

// 新增提醒配置
export function addRemindConfigInfo(data) {
  return request({
    url: '/manage/remindConfigInfo',
    method: 'post',
    data: data
  })
}

// 修改提醒配置
export function updateRemindConfigInfo(data) {
  return request({
    url: '/manage/remindConfigInfo',
    method: 'put',
    data: data
  })
}

// 删除提醒配置
export function delRemindConfigInfo(id) {
  return request({
    url: '/manage/remindConfigInfo/' + id,
    method: 'delete'
  })
}
