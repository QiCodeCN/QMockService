// @ts-ignore
import React from "react";
import { ProTable, ProColumns} from '@ant-design/pro-components';

type apiItem ={
  name: string,
  method: string,
  path: string,
  desc: string
};

const apiolumns: ProColumns<apiItem>[] = [
  {
    title: '标题名称',
    dataIndex: 'name',
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
    return (
      <ProTable<apiItem>
        columns={apiolumns}
      >

      </ProTable>
    )
};
