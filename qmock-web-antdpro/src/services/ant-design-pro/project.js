import {request} from 'umi';

export async function getProductList() {
    return request('/api/mock/project/list', {
        method: 'GET'
    });
}

export async function saveProduct(data) {
    return request('/api/mock/project/save', {
        method: 'POST',
        data
    });
}

export async function removeProduct(id) {
    return request('/api/mock/project/remove', {
        method: 'POST',
        params: {id: id}
    });
}

export async function searchProducts(params) {
    return request('/api/mock/project/search', {
        method: 'GET',
        params
    });
}
