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
