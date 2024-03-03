import React, { useState, useEffect } from 'react';
import { Link } from 'umi'

// 引入组件依赖
import { Button, Space, Table, Modal, Form, Input, message,Pagination,Tag } from "antd";
const { TextArea } = Input;
const { confirm } = Modal;
import { Row, Col } from 'antd';

import { ExclamationCircleOutlined } from '@ant-design/icons';

// 导入sever接口请求方法
import { getProductList, searchProducts, removeProduct } from "@/services/ant-design-pro/project.js";
import UpsertProject from "@/pages/Project/components/UpsertProject";

const Project = () => {
  // 获取全部项目数据
  // const {data:useProjectList, error, loading, run: reloadProjectList} = useRequest(getProductList);
  const [sName, setSName] = useState('');
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [current, setCurrent] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [total, setTotal] = useState();

  const fetchData = (name, current, pageSize) => {
    setLoading(true);
    searchProducts({name:name, current:current, pageSize: pageSize})
      .then(reps => {
        setData(reps.data);
        setTotal(reps.total);
        setLoading(false);
      });
  };

  const formatType = (txtType) =>{
    if (txtType === 'public') {
      return <Tag>公共项目</Tag>
    } else if (txtType === 'private') {
      return <Tag>私有项目</Tag>
    } else {
      return <Tag>未知类型</Tag>
    }
  }

  // 表单列
  const projectColumns = [
    {dataIndex:"id",title:"编号",},
    {dataIndex:"name",title:"名称",},
    {dataIndex:"desc",title:"描述",},
    {dataIndex:"type",title:"类型",
      render: (text, _) => (formatType(text)),
    },
    {dataIndex:"owner",title:"负责人",},
    {dataIndex:"updateDate",title:"更新时间",},
    {dataIndex:"option",title:"操作",
      render: (text, record) => (
        <Space>
          <Link to={'/project/interface?id='+record.id}>接口管理</Link>
          <a onClick={() => editAction(record)}>编辑</a>
          <a onClick={() => deleteConfirmWithPromise(record)}>删除</a>
        </Space>
      ),
    },
  ]

  /** 项目增加对话框相关内容 **/
    // 定义表单控制和隐藏，处理相关动作
  const [projectVisible, setProjectVisible] = useState(false);
  const addAction = () => {
    // setProjectVisible(true);
    setUpsertAction("ADD");
    setUpsertVisible(true);
  }

  // 经创建的 form 控制实例
  const [formProject] = Form.useForm();

  /** 项目编辑对话框 **/
  const [projectEditVisible, setProjectEditVisible] = useState(false);
  const [projectInfo, setProjectInfo] = useState({});
  const [formEditProject] = Form.useForm();
  const editAction = (record) => {
    // setProjectInfo(record);
    // formEditProject.setFieldsValue(record);
    // setProjectEditVisible(true);
    setUpsertDetail(record);
    setUpsertAction('EDIT');
    setUpsertVisible(true);
  }

  // 删除操作
  const deleteConfirmWithPromise = (record) => {
    confirm({
      title: '删除确认？',
      icon: <ExclamationCircleOutlined />,
      content: `确定要删除【${record?.name}】吗?`,
      async onOk() {
        const result = await removeProduct(record.id);
        if(result.success){
          message.success('删除项目成功！');
          fetchData(sName, current, pageSize);
        } else{
          message.error('删除项目失败！');
        }
      },
      onCancel() {},
    });
  };

  /* Components 需要留在上级的变量（重新定义原有的将注释掉）*/
  const [upsertVisible, setUpsertVisible] = useState(false);
  const [upsertAction, setUpsertAction] = useState('ADD');
  const [upsertDetail, setUpsertDetail] = useState({});

  /* 分页相关 */
  const pageChange = (page, size) => {
    // 如果改变了size 将其page设置为第一页
    let tmpPage = 1;
    if(size !== pageSize){
      tmpPage = 1
    } else {
      tmpPage = page;
    }
    setCurrent(tmpPage);
    setPageSize(size);
    fetchData(sName, tmpPage, size);
  }

  useEffect(() => {
    fetchData(sName, current, pageSize);
  }, []);

  // 搜索
  const onSearch = (values) => {
    fetchData(values?.searchName, current, pageSize);
    setSName(values?.searchName);
  };

  // 返回渲染的组件
  return (
    <>
      <Row>
        <Col span={18}>
          <Form layout="inline"
                onFinish={onSearch}
          >
            <Form.Item name="searchName" label="项目名称">
              <Input placeholder="输入项目名关键词可模糊搜索" allowClear />
            </Form.Item>
            <Form.Item>
              <Button htmlType="submit">搜索</Button>
            </Form.Item>
          </Form>
        </Col>
        <Col span={6}>
          <Button
            onClick={addAction}
            type="primary"
            style={{
              marginBottom: 16,
              marginRight: 20,
              float: "right"

            }}
          >
            项目添加
          </Button>
        </Col>
      </Row>
      <Table
        loading={loading}
        rowKey="id"
        pagination={false}
        columns={projectColumns}
        dataSource={data}
      />
      <br/>
      <Pagination
        total={total}
        current={current}
        pageSize={pageSize}
        onChange={pageChange}
        showSizeChanger={true}
        pageSizeOptions={[5,10,20,30,50]}
      />
      <UpsertProject
        upsertAction={upsertAction}
        upsertVisible={upsertVisible}
        setUpsertVisible={setUpsertVisible}
        upsertDetail={upsertDetail}
        reloadProjectList={fetchData}
        upsertName={name}
        upsertCurrent={current}
        upsertPageSize={pageSize}
      />
    </>
  );
};

export default Project
