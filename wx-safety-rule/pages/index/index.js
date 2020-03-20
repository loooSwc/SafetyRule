//index.js
const app = getApp()
const http = require("../../util/http");
let page = require('../../util/page')(10);

Page({
  data: {
    ruleList: []
  },
  params: { kw: '' },
  onLoad: function() {
    this.query();
  },
    onReachBottom: function () {
    let queryParams = {};
    
    queryParams.kw = this.params.kw;

    if (!page.isDone()) {
      page.next();
      http.queryPage('index/search', queryParams, page).then(data => {
        this.setData({ ruleList: this.data.ruleList.concat(data.result) });
      });
    }
  },
  query: function () {
    page.reset();
    let queryParams = {};
  
    queryParams.kw = this.params.kw;

    http.queryPage('index/search', queryParams, page).then(data => {
      this.setData({ ruleList: data.result });
    });
  },

  search: function (event) {
    this.params.kw = event.detail.value;
    this.query();
  },
})
