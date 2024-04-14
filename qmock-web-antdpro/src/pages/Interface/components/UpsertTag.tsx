import React, { useEffect, useState } from "react";
import { Form, Input, Modal } from "antd";
const { TextArea } = Input;
import { saveTag } from "@/services/ant-design-pro/interface"
/* 抽离出来的组件，用于优化页面代码 */
const UpsertTag = (props) => {
    const [form] = Form.useForm();

    // 副作用钩子，给定一个参数，当props内容有变化是执行此Hook
    useEffect(()=>{
        if(props.upsertAction==='EDIT'){
            form.setFieldsValue(props.tagInfo);
        } else {
            form.resetFields();
        }
    },[props]) //

    return (
        <Modal
            title={props.upsertAction==='ADD'?'增加分类':'修改分类'} // 动态判断标题
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
                            projectId: props.projectId,
                            desc: values.desc,
                            operator: '大奇'
                        }
                        const resp = await saveTag(data);
                        if (resp.success) {
                            form.resetFields(); // 表单清除历史
                            props.setUpsertVisible(false);
                            props.reloadTagList();
                        }
                    })
                    .catch((info) => {
                        console.log('保存分类信息失败', info);
                    });
            }}
        >
            <Form form={form}>
                <Form.Item hidden={props.upsertAction==='ADD'} name='id' label='编号'>
                    <Input disabled></Input>
                </Form.Item>
                <Form.Item
                    name='name'
                    label='名称'
                    rules={[
                        {
                            required: true,
                            message: '分类名称为必填项！',
                        },
                    ]}
                >
                    <Input placeholder="请输入分类名称"></Input>
                </Form.Item>
                <Form.Item name="desc" label="可以备注更多信息">
                    <TextArea/>
                </Form.Item>
            </Form>
        </Modal>
    )
}

export default UpsertTag;
