import {request} from "@/.umi/exports";

export async function searchInterface(
  params: {
    // query
    /** 当前的页码 */
      current?: number;
    /** 页面的容量 */
      pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/mock/interface/list', {
    method: 'POST',
    data: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function getTagList(
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/mock/tag/list', {
    method: 'GET',
    ...(options || {}),
  });
}

export async function saveTag(data) {
  return request('/api/mock/tag/save', {
    method: 'POST',
    data
  });
}

export async function getTagInfo(tagId: Number) {
  return request('/api/mock/tag/info', {
    method: 'GET',
    params: {tagId: tagId}
  });
}

export async function removeTag(tagId: Number) {
  return request('/api/mock/tag/remove', {
    method: 'POST',
    params: {tagId: tagId}
  });
}

export async function saveApi(data) {
  return request('/api/mock/interface/upsert', {
    method: 'POST',
    data
  });
}

