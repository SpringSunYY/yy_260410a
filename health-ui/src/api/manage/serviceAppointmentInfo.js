import request from '@/utils/request'

// 查询服务预约列表
export function listServiceAppointmentInfo(query) {
  return request({
    url: '/manage/serviceAppointmentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询服务预约详细
export function getServiceAppointmentInfo(id) {
  return request({
    url: '/manage/serviceAppointmentInfo/' + id,
    method: 'get'
  })
}

// 新增服务预约
export function addServiceAppointmentInfo(data) {
  return request({
    url: '/manage/serviceAppointmentInfo',
    method: 'post',
    data: data
  })
}

// 修改服务预约
export function updateServiceAppointmentInfo(data) {
  return request({
    url: '/manage/serviceAppointmentInfo',
    method: 'put',
    data: data
  })
}

// 删除服务预约
export function delServiceAppointmentInfo(id) {
  return request({
    url: '/manage/serviceAppointmentInfo/' + id,
    method: 'delete'
  })
}
