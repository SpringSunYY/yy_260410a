import request from '@/utils/request'

// 查询居民信息列表
export function listResidentInfo(query) {
  return request({
    url: '/manage/residentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询居民信息详细
export function getResidentInfo(id) {
  return request({
    url: '/manage/residentInfo/' + id,
    method: 'get'
  })
}

// 新增居民信息
export function addResidentInfo(data) {
  return request({
    url: '/manage/residentInfo',
    method: 'post',
    data: data
  })
}

// 修改居民信息
export function updateResidentInfo(data) {
  return request({
    url: '/manage/residentInfo',
    method: 'put',
    data: data
  })
}

// 删除居民信息
export function delResidentInfo(id) {
  return request({
    url: '/manage/residentInfo/' + id,
    method: 'delete'
  })
}
