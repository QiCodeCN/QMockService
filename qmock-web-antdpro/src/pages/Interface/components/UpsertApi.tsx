// @ts-ignore
import React, { useEffect, useState,useRef } from "react";
// @ts-ignore
import {Form, Input, message, Modal} from "antd";
import {
  ProFormInstance,
  DrawerForm,
  ProForm,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormDigit,
  ProFormTextArea
} from '@ant-design/pro-components';
const { TextArea } = Input;
import { saveApi } from "@/services/ant-design-pro/interface"
/* 抽离出来的组件，用于优化页面代码 */
// @ts-ignore
const UpsertApi = (props) => {
  const formRef = useRef<ProFormInstance>();
  useEffect(()=>{
    if(props.action==='EDIT'){
      formRef?.current?.setFieldsValue(props.apiInfo);
    } else {
      formRef?.current?.resetFields();
    }
    console.log(props.action);
  },[props])

  const saveApiAction = () =>{
    // @ts-ignore
    formRef.current.validateFields()
      .then(async (values) => {
        const data = {
          id: props.action==='ADD'? undefined: values.id,
          tagId: values.tagId,
          projectId: props.projectId,
          title: values.title,
          method: values.method,
          path: values.path,
          // enable: 0,
          responseCode:values.responseCode,
          responseDefault: values.responseDefault,
          desc: values.desc,
          createUser: 'Qi',
          updateUser: 'Qi'
        }
        const resp = await saveApi(data);
        if (resp?.success) {
          formRef?.current?.resetFields()
          message.success('保存接口成功！');
          props.setVisible(false)
          props.refTable.current.reload();
          return true
        } else{
          message.error('保存接口失败！');
          return false
        }
      })
  }
  return (
      <DrawerForm
        drawerProps={{
          destroyOnClose: true,
        }}
        open={props.visible}
        onOpenChange={props.setVisible}
        initialValues={props.apiInfo}
        title={props.action ==="EDIT" ? "修改接口" : "增加接口"}
        onFinish={async (values) => {saveApiAction()}}
      >
        <ProForm formRef={formRef} submitter={false}>
          <ProFormText
            hidden={props.action==='ADD'}
            name="id"
            label="ID"
            disabled
          />
          <ProFormText
            name="title"
            label="标题"
            rules={[{required: true, message: '请输入标题'}]}
          />
          <ProFormSelect
            name="tagId"
            label="分组"
            request={ () => props.tagData}
            rules={[{required: true, message: '请选择分组'}]}
          />
          <ProFormSelect
            name="method"
            label="方法"
            valueEnum={{
              get: 'GET',
              post: 'POST',
            }}
            rules={[{required: true, message: '请选择接口方法'}]}
          />
          <ProFormText
            name="path"
            label="路径"
            rules={[{required: true, message: '请输入正确路径'}]}
          />
          <ProFormDigit
            name="responseCode"
            label="返回码"
            initialValue={200}
            rules={[{required: true, message: '请输入正确路径'}]}
          />

          {/*<ProFormRadio.Group*/}
          {/*  name="responseCode"*/}
          {/*  style={{*/}
          {/*    margin: 16,*/}
          {/*  }}*/}
          {/*  label="返回类型"*/}
          {/*  radioType="button"*/}
          {/*  initialValue="json"*/}
          {/*  options={[*/}
          {/*    {*/}
          {/*      label: 'JSON格式',*/}
          {/*      value: 'json',*/}
          {/*    },*/}
          {/*    {*/}
          {/*      label: 'XML格式',*/}
          {/*      value: 'xml',*/}
          {/*    },*/}
          {/*  ]}*/}
          {/*/>*/}
          <ProFormTextArea
            name="responseDefault"
            label="默认返回"
          />
          <ProFormTextArea
          name="desc"
          label="备注"
          />
        </ProForm>
      </DrawerForm>
  )
}

export default UpsertApi;
