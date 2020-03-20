class TablePage {
  pageNumber = 1;
  pageSize = 10;
  result;
  params;
  totalCount = 0;
  totalPage = 1;

  constructor(_pageSize = 10) {
    this.pageSize = _pageSize;
  }

  next() {
    this.pageNumber++;
  }

  isDone(){
    return this.pageNumber === this.totalPage;
  }

  reset(){
    this.pageNumber = 1;
    this.result = undefined;
    this.params = undefined;
    this.totalCount = 0;
    this.totalPage = 1;
  }
}

module.exports = function(...params){
  return new TablePage(...params);
};