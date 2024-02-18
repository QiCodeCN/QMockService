import React, { useEffect, useState } from "react";
import { Form, Input, Modal } from "antd";
const { TextArea } = Input;

import { saveProduct } from "@/services/ant-design-pro/project.js";

/* 抽离出来的组件，用于优化页面代码 */
const UpsertProject = (props) => {
    const [form] = Form.useForm();

    // 副作用钩子，给定一个参数，当props内容有变化是执行此Hook
    useEffect(()=>{
        if(props.upsertAction==='EDIT'){
            form.setFieldsValue(props.upsertDetail);
        } else {
            form.resetFields();
        }
    },[props]) //

    return (
        <Modal
            title={props.upsertAction==='ADD'?'增加项目':'修改项目'} // 动态判断标题
            open={props.upsertVisible}
            destroyOnClose
            onCancel={()=>props.setUpsertVisible(false)}
            onOk={() => {
                form
                    .validateFields()
                    .then(async (values) => {
                        const data = {
                            id: props.upsertAction==='ADD'? undefined: values.id, // 根据增加还是修改给定id值
                            name: values.name,
                            owner: values.owner,
                            desc: values.desc,
                            type: 'public',
                            operator: '大奇'
                        }
                        const resp = await saveProduct(data);
                        if (resp.success) {
                            form.resetFields(); // 表单清除历史
                            props.setUpsertVisible(false);
                            props.reloadProjectList(props.upsertName, props.upsertCurrent, props.upsertPageSize);
                        }
                    })
                    .catch((info) => {
                        console.log('保存项目信息失败', info);
                    });
            }}
        >
            <Form form={form}>
                {/*通过hidden属性决定是否隐藏此项目，在新增操作时候隐藏*/}
                <Form.Item hidden={props.upsertAction==='ADD'} name='id' label='编号'>
                    <Input disabled></Input>
                </Form.Item>
                <Form.Item
                    name='name'
                    label='名称'
                    rules={[
                        {
                            required: true,
                            message: '项目名称为必填项！',
                        },
                    ]}
                >
                    <Input placeholder="请输入项目名称"></Input>
                </Form.Item>
                <Form.Item name='owner' label='负责人'>
                    <Input placeholder="项目相负责人"></Input>
                </Form.Item>
                <Form.Item name="desc" label="更多信息">
                    <TextArea/>
                </Form.Item>
            </Form>
        </Modal>
    )
}

export default UpsertProject;
