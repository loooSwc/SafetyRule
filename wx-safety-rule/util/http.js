var Promise = require('../assets/lib/es6-promise-auto-min.js').Promise;
const config = require('./config');


class Http {
  interceptor;
  defaultHeader = {
    'content-type': 'application/json',
    'x-origion': config.apiPrefix,
    'x-version': config.version
  };

  constructor() {}

  post(url, params, _header = {}) {
    
    let header = Object.assign({}, this.defaultHeader, _header);
    if (this.interceptor && (typeof this.interceptor.request) === 'function') {
      this.interceptor.request(url, params, header);
    }
    return new Promise((resolve, reject) => {
      wx.request({
        url: config.host + '/' + url,
        method: "POST",
        data: params,
        header: header,
        dataType: "json",
        success: function(res) {
          if (res.statusCode === 200) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(error) {
          reject(error);
        },
        complete: function(rep) {
          if(rep && rep.header){
            let cookied = rep.header['Set-Cookie'] || rep.header['set-cookie'];
            if (cookied) {
              http.setDefaultHeader('Cookie', cookied);
            }
            
          }
        }
      })
    }).then(data => {
      if (this.interceptor && (typeof this.interceptor.response) === 'function') {
        return this.interceptor.response(url, data, header);
      }
      return data;
    }, error => {
      if (this.interceptor && (typeof this.interceptor.responseError) === 'function') {
        return this.interceptor.responseError(url, data, header);
      }
      return error;
    });
  }

  queryPage(url, params, page, header) {
    page.params = params;
    return this.post(url, page, header).then(data => {
      page.pageNumber = data.currentPageNo;
      page.result = data.result;
      page.totalCount = data.totalCount;
      page.totalPage = data.totalPageCount;
      page.params = {};
      return page;
    }, error => {

    });
  }

  fileUpload(url, filePath, params, _header = {}) {

    let header = Object.assign({}, this.defaultHeader, _header, {
      'content-type': 'multipart/form-data'
    });
    if (this.interceptor && (typeof this.interceptor.request) === 'function') {
      this.interceptor.request(url, params, header);
    }

    return new Promise((resolve, reject) => {

      wx.uploadFile({
        url: config.host + '/' + config.apiPrefix + '/' + url,
        filePath: filePath,
        name: 'file',
        formData: params,
        header: header,
        success: function(res) {
          if (res.statusCode === 200) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(error) {
          reject(error);
        },
        complete: function(rep) {
          
        }
      })
    }).then(data => {
      if (this.interceptor && (typeof this.interceptor.response) === 'function') {
        return this.interceptor.response(url, data, header);
      }
      return data;
    }, error => {
      if (this.interceptor && (typeof this.interceptor.responseError) === 'function') {
        return this.interceptor.responseError(url, data, header);
      }
      return error;
    });
  }

  setInterceptor(_interceptor) {
    this.interceptor = _interceptor;
  }

  setDefaultHeader(key, value) {
    this.defaultHeader[key] = value;
  }
}

const http = new Http();

http.setInterceptor({
  request: function(url, params, header) {
    header['x-date'] = (new Date()).getTime();
    let data = 'x-origion=' + header['x-origion'] + '&x-date=' + header['x-date'] + (header['wxid'] ? '&wxid=' + header['wxid'] : '');
  },
  response: function(url, data, header) {
    return new Promise((resolve, reject) => {
      if (data.code) {
        switch (data.code) {
          case '000001':
            {
              reject('Signature 认证失败');
              break;
            }
          case '000002':
            {
              reject('未绑定平台用户');
              break;
            }
          case '000003':
            {
              reject('平台用户状态不正常(冻结|已删除)');
              break;
            }
          default:
            {
              resolve(data);
            }
        }
      }
      resolve(data);
    });
  }
});

module.exports = http;