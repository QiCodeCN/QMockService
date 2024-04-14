// @ts-ignore
import React, {useRef, useState} from "react";
import { useSearchParams } from 'umi'
import {Tree, Button, message, Modal} from 'antd';
const { confirm, info } = Modal;
import {PlusOutlined, EditOutlined, DeleteOutlined, ExclamationCircleOutlined} from '@ant-design/icons';
import { ProTable, ProColumns, ProCard} from '@ant-design/pro-components';
import { searchInterface, getTagList,getTagInfo, removeTag } from '@/services/ant-design-pro/interface';
import UpsertTag from "@/pages/Interface/components/UpsertTag";
import {ActionType} from "@ant-design/pro-table/lib";

type apiItem ={
  name: string,
  method: string,
  path: string,
  desc: string
};

const Tag = () => {

  const refTable = useRef<ActionType>();
  const [tableData, setTableData] = useState([]);
  const [treeData, setTreeData] = useState([]);
  const [treeSelect, setTreeSelect] = useState(0);
  const [treeSelectInfo, setTreeSelectInfo] = useState({});
  const [searchParams, setSearchParams] = useSearchParams();
  const [btnAdd, setBtnAdd] = useState(false);
  const [btnEdit, setBtnEdit] = useState(true);
  const [btnDelete, setBtnDelete] = useState(true);
  const [upsertVisible, setUpsertVisible] = useState(false);
  const [upsertAction, setUpsertAction] = useState('ADD');

  const fetchApiData = async (params, sort, filter) => {
    const repsTag = await getTagList();
    var treeDataTemp: any[] = []
    repsTag?.data.forEach(function (item) {
      treeDataTemp.push({title: item["name"], key: item["id"]})
    })
    setTreeData([{title: "所有分类", "key":0, "children": treeDataTemp}])
    const repsApi = await searchInterface(params);
    setTableData(repsApi?.data)
    return repsApi;
  }

  const onSelect = (selectedKeys) => {
    if (selectedKeys[0] == 0) {
      setBtnAdd(false)
      setBtnEdit(true)
      setBtnDelete(true)
      setTreeSelect(0)
    } else{
      setTreeSelect(selectedKeys[0])
      setBtnAdd(true)
      setBtnEdit(false)
      setBtnDelete(false)
    }
  };

  const addAction = () => {
    // setProjectVisible(true);
    setUpsertAction("ADD");
    setUpsertVisible(true);
  }

  const editAction = async () => {
    // setProjectVisible(true);
    setUpsertAction("EDIT");
    const repsInfo = await getTagInfo(treeSelect);
    setTreeSelectInfo(repsInfo?.data)
    setUpsertVisible(true);
  }

  const deleteConfirm = (tagId) => {
    if (tableData.length>0){
      info({
        title: '警告!',
        content: '此分类下有数据，无法删除'
      });
    } else {
      confirm({
        title: '删除确认？',
        icon: <ExclamationCircleOutlined />,
        content: `确定要删除选中的分类吗?`,
        async onOk() {
          const result = await removeTag(tagId);
          if(result.success){
            message.success('删除分类成功！');
            setTreeSelect(0)
          } else{
            message.error('删除分类失败！');
          }
        },
        onCancel() {},
      });
    }
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

  return (
    <ProCard split="vertical">
      <ProCard colSpan="300px" ghost>
        <div >
          <div style={{display: "flex", justifyContent:"flex-end", marginTop: 15, marginRight:10}}>
            <Button disabled={btnAdd} onClick={addAction} type="primary" icon={<PlusOutlined /> } style={{margin: 5}}/>
            <Button disabled={btnEdit} onClick={editAction} icon={<EditOutlined />} style={{margin: 5}}/>
            <Button disabled={btnDelete} onClick={()=>deleteConfirm(treeSelect)} danger icon={<DeleteOutlined />} style={{margin: 5}}/>
          </div>
          <Tree
            style={{marginTop: 20, marginLeft:10}}
            onSelect={onSelect}
            autoExpandParent={true}
            treeData={treeData}
          />
        </div>
      </ProCard>
      <ProCard>
        <ProTable
          actionRef={refTable}
          params={{ projectId: searchParams.get('id'), tagId: treeSelect}}
          columns={apiolumns}
          request={fetchApiData}
        >
        </ProTable>
      </ProCard>
      <UpsertTag
        upsertAction={upsertAction}
        upsertVisible={upsertVisible}
        setUpsertVisible={setUpsertVisible}
        tagInfo={treeSelectInfo}
        projectId={searchParams.get('id')}
        reloadTagList={fetchApiData}
      />
    </ProCard>
  )
};
export default Tag

