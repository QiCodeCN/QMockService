import React, {useRef, useState} from "react";
import { useSearchParams } from 'umi'
import {Tree, Button, message, Modal, Space, Popconfirm } from 'antd';
const { confirm, info } = Modal;
import {PlusOutlined, EditOutlined, DeleteOutlined, ExclamationCircleOutlined} from '@ant-design/icons';
import { ProTable, ProColumns, ProCard} from '@ant-design/pro-components';
import { searchInterface, getTagList,getTagInfo, removeTag } from '@/services/ant-design-pro/interface';
import { getRules,removeRule } from '@/services/ant-design-pro/rule';
import UpsertTag from "@/pages/Interface/components/UpsertTag";
import UpsertApi from "@/pages/Interface/components/UpsertApi";
import UpsertRule from "@/pages/Interface/components/UpsertRule";
import {ActionType} from "@ant-design/pro-table/lib";

type apiItem ={
  name: string,
  method: string,
  path: string,
  desc: string
};

const Interface = () => {

  const refTable = useRef<ActionType>();

  const [tableData, setTableData] = useState([]);
  const [treeData, setTreeData] = useState([]);
  const [tagData, setTagData] = useState([]);
  const [treeSelect, setTreeSelect] = useState(0);
  const [treeSelectInfo, setTreeSelectInfo] = useState({});
  const [searchParams, setSearchParams] = useSearchParams();
  const [btnAdd, setBtnAdd] = useState(false);
  const [btnEdit, setBtnEdit] = useState(true);
  const [btnDelete, setBtnDelete] = useState(true);
  const [upsertVisible, setUpsertVisible] = useState(false);
  const [upsertAction, setUpsertAction] = useState('ADD');

  const [apiAction, setApiAction] = useState('ADD');
  const [apiVisible, setApiVisible] = useState(false);
  const [apiRecord, setApiRecord] = useState({});

  const refRuleTable = useRef<ActionType>();
  const [ruleAction, setRuleAction] = useState('ADD');
  const [ruleVisible, setRuleVisible] = useState(false);
  const [ruleRecord, setRuleRecord] = useState({});
  const [expandedKey, setExpandedKey] = useState([]);

  const fetchApiData = async (params, sort, filter) => {
    const repsTag = await getTagList();
    var treeDataTemp: any[] = []
    var tagDataTemp: any[] = []
    repsTag?.data.forEach(function (item) {
      treeDataTemp.push({title: item["name"], key: item["id"]})
      tagDataTemp.push({label: item["name"], value: item["id"]})
    })
    setTreeData([{title: "所有分类", "key":0, "children": treeDataTemp}])
    setTagData(tagDataTemp)

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

  const ruleConfirmDelete = async (id) => {
    console.log(id);
    const result = await removeRule(id)
    if(result.success){
      // @ts-ignore
      refRuleTable.current.reload()
      message.success('删除规则成功！');
    } else{
      message.error('删除规则失败！');
    }
  };

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
      title: 'ID',
      dataIndex: 'id',
      hidden: true
    },
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
      dataIndex: 'option',
      render: (text, record) => (
        <Space>
          <a onClick={()=>{
            if (record.id === expandedKey[0] ) {
              setExpandedKey([])
            } else{
              setExpandedKey([record?.id])
            }
          }}>规则配置</a>
          <a onClick={() => {
            setApiRecord(record)
            setApiAction("EDIT")
            setApiVisible(true)
          }}>修改接口</a>
        </Space>
      ),
    },
  ]

  // 嵌套表格：api内规则
  const expandedRowRender = (record) => {
    return (
      <ProTable
        actionRef={refRuleTable}
        columns={[
          { title: 'ID', dataIndex: 'id', key: 'id' },
          { title: '名称', dataIndex: 'title', key: 'title' },
          { title: '规则类型', dataIndex: 'type', key: 'type' },
          { title: '状态', dataIndex: 'enable', key: 'enable' },
          { title: '匹配规则', dataIndex: 'requestFilter', key: 'requestFilter', ellipsis: true},
          { title: '匹配返回内容', dataIndex: 'responseBody', key: 'responseBody', ellipsis: true},
          { title: '返回状态码', dataIndex: 'responseCode', key: 'responseCode', ellipsis: true },
          {
            title: '操作',
            dataIndex: 'operation',
            key: 'operation',
            valueType: 'option',
            render: (text, record) => (
              <Space>
                <a onClick={() => {
                  setRuleRecord(record)
                  setRuleAction("EDIT")
                  setRuleVisible(true)
                }}>修改接口</a>
                <Popconfirm
                  title="删除警告"
                  description="确认要删除此规则吗？"
                  onConfirm={()=>ruleConfirmDelete(record.id)}
                  onCancel={()=>{}}
                  okText="确定"
                  cancelText="取消"
                >
                  <a>删除</a>
                </Popconfirm>

              </Space>
            ),
          },
        ]}
        headerTitle="规则管理"
        search={false}
        options={false}
        toolBarRender={() => [
          <Button
            key="addInterface"
            icon={<PlusOutlined />}
            onClick={() => {
              setRuleRecord({
                apiId: record.id,
                type: "simple",
                enable: 1
              })
              setRuleAction("ADD")
              setRuleVisible(true)
            }}
            type="primary"
          >
            添加规则
          </Button>,
        ]}
        params={{ apiId: record.id }}
        request={async (
          params,
          sort,
          filter,
        ) => {
          const msg = await getRules(params);
          return msg
        }}
        pagination={false}
      />
    );
  };

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
          rowKey="id"
          expandedRowKeys={expandedKey}
          expandable={ {expandedRowRender} }
          toolBarRender={() => [
            <Button
              key="addInterface"
              icon={<PlusOutlined />}
              onClick={() => {
                setApiRecord({})
                setApiAction("ADD")
                setApiVisible(true)
              }}
              type="primary"
            >
              添加接口
            </Button>,
          ]}
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
      <UpsertApi
        visible={apiVisible}
        action={apiAction}
        setVisible={setApiVisible}
        refTable={refTable}
        projectId={searchParams.get('id')}
        apiInfo={apiRecord}
        tagData={tagData}
      />
      <UpsertRule
        visible={ruleVisible}
        action={ruleAction}
        setVisible={setRuleVisible}
        refTable={refRuleTable}
        ruleInfo={ruleRecord}
      />
    </ProCard>
  )
};
export default Interface

