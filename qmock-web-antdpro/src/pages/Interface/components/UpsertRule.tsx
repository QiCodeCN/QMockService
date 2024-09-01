// @ts-ignore
import React, { useEffect, useState,useRef } from "react";
// @ts-ignore
import {Form, Input, message, Modal} from "antd";
import {
  ProFormInstance,
  DrawerForm,
  ProForm,
  ProFormSwitch,
  ProFormSelect,
  ProFormText,
  ProFormDigit,
  ProFormDependency,
  ProFormTextArea
} from '@ant-design/pro-components';
const { TextArea } = Input;
import { saveRule } from "@/services/ant-design-pro/rule"
/* 抽离出来的组件，用于优化页面代码 */
// @ts-ignore
const UpsertRule = (props) => {
  const formRef = useRef<ProFormInstance>();
  useEffect(()=>{
    if(props.action==='EDIT'){
      formRef?.current?.setFieldsValue(props.ruleInfo);
    } else {
      formRef?.current?.resetFields();
      formRef?.current?.setFieldsValue(props.ruleInfo);
    }
  },[props])

  const saveRuleAction = () =>{
    // @ts-ignore
    formRef.current.validateFields()
      .then(async (values) => {
        const data = {
          id: props.action==='ADD'? undefined: values.id,
          apiId: values.apiId,
          title: values.title,
          type: values.type,
          enable: values.enable,
          requestFilter: values.requestFilter,
          responseCode:values.responseCode,
          responseBody: values.responseBody,
          shell: values.shell,
          createUser: 'Qi',
          updateUser: 'Qi'
        }
        const resp = await saveRule(data);
        if (resp?.success) {
          formRef?.current?.resetFields()
          message.success('保存规则成功！');
          props.setVisible(false)
          props.refTable.current.reload();
          return true
        } else{
          message.error('保存规则失败！');
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
        initialValues={props.ruleInfo}
        title={props.action ==="EDIT" ? "修改规则" : "增加规则"}
        onFinish={async (values) => {saveRuleAction()}}
      >
        <ProForm formRef={formRef} submitter={false}>
          <ProFormText
            hidden={props.action==='ADD'}
            name="id"
            label="ID"
            disabled
          />
          <ProFormText
            name="apiId"
            label="归属Api"
            disabled
          />
          <ProFormText
            name="title"
            label="标题"
            rules={[{required: true, message: '请输入标题'}]}
          />
          <ProFormSwitch
            name="enable"
            label="启用"
          />
          <ProFormSelect
            name="type"
            label="类型"
            valueEnum={{
              simple: '简单配置(默认)',
              senior: '高级配置',
            }}
            rules={[{required: true, message: '请选择规则类型'}]}
          />
          <ProFormTextArea
            name="responseBody"
            label="规则返回体"
          />
          <ProFormDependency  name={['type']}>
            {({ type }) => {
              if (type === 'senior') {
                return (
                  <span>
                    <ProFormDigit
                      name="responseCode"
                      label="返回码"
                      initialValue={200}
                    />
                    <ProFormDigit
                      name="responseDelay"
                      label="返回延迟(ms)"
                    />
                  </span>
                );
              }
            }}
          </ProFormDependency>
        </ProForm>
      </DrawerForm>
  )
}

export default UpsertRule
