import request from '@/utils/request'

// 查询住院记录列表
export function listHospitalizationInfo(query) {
  return request({
    url: '/manage/hospitalizationInfo/list',
    method: 'get',
    params: query
  })
}

// 查询住院记录详细
export function getHospitalizationInfo(id) {
  return request({
    url: '/manage/hospitalizationInfo/' + id,
    method: 'get'
  })
}

// 新增住院记录
export function addHospitalizationInfo(data) {
  return request({
    url: '/manage/hospitalizationInfo',
    method: 'post',
    data: data
  })
}

// 修改住院记录
export function updateHospitalizationInfo(data) {
  return request({
    url: '/manage/hospitalizationInfo',
    method: 'put',
    data: data
  })
}

// 删除住院记录
export function delHospitalizationInfo(id) {
  return request({
    url: '/manage/hospitalizationInfo/' + id,
    method: 'delete'
  })
}
