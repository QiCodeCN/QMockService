import {request} from "@/.umi/exports";

export async function getRules(
  params: {
    // query
    /** 当前的页码 */
      current?: number;
    /** 页面的容量 */
      pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/mock/rule/list', {
    method: 'POST',
    data: {
      ...params,
    },
    ...(options || {}),
  });
}


export async function saveRule(data) {
  return request('/api/mock/rule/upsert', {
    method: 'POST',
    data
  });
}

export async function removeRule(id: Number) {
  return request('/api/mock/rule/remove', {
    method: 'POST',
    params: {id: id}
  });
}
