import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} 非典型程序员-Mock实战测试开发项目`}
      links={[
        {
          key: 'QMockService',
          title: 'Mock项目',
          href: 'https://github.com/QiCodeCN/QMockService/',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/QiCodeCN/QMockService/',
          blankTarget: true,
        },
        {
          key: 'TPMServer',
          title: '提测平台',
          href: 'https://github.com/QiCodeCN/TestProjectManagement',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
