// @ts-ignore
import React from "react";
import { useSearchParams } from 'umi'
import { ProTable, ProColumns} from '@ant-design/pro-components';
import { searchInterface } from '@/services/ant-design-pro/interface';

type apiItem ={
  name: string,
  method: string,
  path: string,
  desc: string
};

export const waitTimePromise = async (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

export const waitTime = async (time: number = 100) => {
  await waitTimePromise(time);
};



const fetchApiData = async (params, sort, filter) => {
  const reps = await searchInterface(params);
  return reps;
};

const apiolumns: ProColumns<apiItem>[] = [
  {
    title: '标题名称',
    dataIndex: 'title',
    ellipsis: true,
  },
  {
    title: '接口方法',
    dataIndex: 'method',
    ellipsis: true,
  },
  {
    title: '请求路径',
    dataIndex: 'path',
    ellipsis: true,
  },
  {
    title: '接口用途说明',
    dataIndex: 'desc',
    hideInSearch: true,
    ellipsis: true,
  },
  {
    title: '管理操作',
    valueType: 'option',
    key: 'option',
  },
]
export default () => {
    const [searchParams, setSearchParams] = useSearchParams();
    return (
      <ProTable
        params={{ projectId: searchParams.get('id') }}
        columns={apiolumns}
        request={fetchApiData}
      >

      </ProTable>
    )
};
