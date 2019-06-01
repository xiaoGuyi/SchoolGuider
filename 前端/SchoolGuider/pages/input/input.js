// pages/input/input.js
const util = require( "../../utils/util.js" );
const app = getApp();
var that;

Page({
  data: {
    imageUrls: [],
    pageIntroduce: ["中文", "英语", "日语"],
    pageIndex: 0,

    scenicName: "",
    introduce: "",
    imageNames: [],
    voiceName: "",

    windowWidth: '',//窗口宽度
    windowHeight: '',//窗口高度

    searchData: "",
    keyword: "",
    cid:0,
    
    //editor
    formats: {},
    bottom: 0,
    readOnly: false,
    placeholder: '开始输入...',
    _focus: false,

    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '添加内容', //导航栏 中间的标题
    },

    // 此页面 页面内容距最顶部的距离
    height: app.globalData.height * 2 + 20,
  },

  /*editor methods */
  readOnlyChange() {
    this.setData({
      readOnly: !this.data.readOnly
    })
  },
  onEditorReady() {
    const that = this
    wx.createSelectorQuery().select('#editor').context(function (res) {
      that.editorCtx = res.context
    }).exec()
  },

  undo() {
    this.editorCtx.undo()
  },
  redo() {
    this.editorCtx.redo()
  },
  format(e) {
    let { name, value } = e.target.dataset
    if (!name) return
    // console.log('format', name, value)
    this.editorCtx.format(name, value)

  },
  onStatusChange(e) {
    const formats = e.detail
    this.setData({ formats })
  },
  insertDivider() {
    this.editorCtx.insertDivider({
      success: function () {
        console.log('insert divider success')
      }
    })
  },
  clear() {
    this.editorCtx.clear({
      success: function (res) {
        console.log("clear success")
      }
    })
  },
  removeFormat() {
    this.editorCtx.removeFormat()
  },
  insertDate() {
    const date = new Date()
    const formatDate = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`
    this.editorCtx.insertText({
      text: formatDate
    })
  },
  insertImage() {
    const that = this
    wx.chooseImage({
      count: 1,
      sourceType: [],
      success: function (res) {
        
        // 上传图片
        var uploadUrl = util.uploadFileUrl;
        wx.uploadFile({
          url: uploadUrl,
          filePath: res.tempFilePaths[0],//图片路径，如tempFilePaths[0]
          name: 'file',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            file_name: res.tempFilePaths[0]
          },
          success: function (res) {
            // res为后台存储的图片名
            var obj = JSON.parse(res.data);
            that.editorCtx.insertImage({
              src: util.serverUrl + obj.file_name,
              data: {
                id: 'abcd',
                role: 'god'
              },
              success: function () {
                console.log('insert image success')
              }
            })
          }
        })
      }
    })
  },
  introduceInput() {
    var that = this;
    this.editorCtx.getContents({
      success: function (e) {
        that.setData({
          introduce: e.html
        })
        console.log(e.html)
      },
    })
  },

  /*高亮提示框 method*/
  chooseSearchResultAction: function (e) {
    var index = e.currentTarget.dataset.id;
    var value = this.data.searchData[index].name;
    var cid = this.data.searchData[index].cid;
    this.setData({
      keyword: value,
      cid: cid
    })
  },

  /**
   * 输入监听
   */

  searchInputAction: function (e) {
    console.log(e)
    let that = this
    let value = e.detail.value

    if (value.length == 0) {
      this.setData({
        searchData: []
      })
      return
    }

    wx.request({
      url: util.getRecordUrls[0],
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      data: {
        "search": value
      },
      success: function (res) {
        var searchData;
        searchData = res.data.map(function (res) {
          return { key: value, name: res.scenicName, cid: res.cid }
        })
        that.setData({
          searchData: searchData,
        })
      },
      fail: function (res) { },
    })
  },
  // 页内使用逗号分隔，不同语种使用分号分隔
  getImageNameList() {
    var nameList = this.data.imageNames;
    var res = "";
    console.log( nameList );
    for( var i = 0; i < nameList.length; ++i ) {
      res += nameList[i];
      console.log( nameList[i] )
      res += ",";
    }
    return res
  },

  /*生成二维码*/
  generateQRCode() {
    if (this.data.scenicName.replace(/\s+/g, '') == '') {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '景点名不能为空',
      })
      return
    }
    wx.showLoading({
      title: "正在生成"
    })
    const that = this;
    console.log("cid:" + that.data.cid)
    // 首先把本页内容加载到app.globalData中，然后传到服务器
    wx.request({
      url: util.uploadRecordUrls[this.data.pageIndex],
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      data: {
        "scenicName": this.data.scenicName,
        "introduce": this.data.introduce,
        "voiceName": this.getImageNameList(),
        "imageNameList": this.data.voiceName,
        "cid":this.data.cid,
      },
      success: function (e) {
        app.globalData.recordId = e.data;
        wx.hideLoading();
        var judge = e.data;
        wx.hideLoading();
        console.log('judge:'+judge);
        if (!isNaN(judge)) {
          wx.showModal({
            title: '上传成功',
            showCancel: false,
            success(res) {
              if (res.confirm) {
                wx.navigateTo({
                  url: "../QRCode/QRCode"
                })
              }
            }
          })
        }
        else {
          wx.showModal({
            title: '该记录已存在',
            showCancel: false,
            success(res) {
            }
          })
        }
      }
    })
  },

  /*提交到服务器*/
  submit(){
    if (this.data.scenicName.replace(/\s+/g, '') == '') {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '景点名不能为空',
      })
      return
    }
  if(that.data.cid!= 0){
    // 首先把本页内容加载到app.globalData中，然后传到服务器
    wx.request({
      url: util.uploadRecordUrls[this.data.pageIndex],
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      data: {
        "scenicName": this.data.scenicName,
        "introduce": this.data.introduce,
        "voiceName": this.getImageNameList(),
        "imageNameList": this.data.voiceName,
        "cid": this.data.cid
      },
      success: function (e) {
        var judge = e.data;
        console.log("judge" + judge)
        wx.hideLoading();
        if(!isNaN(judge)){
          wx.showModal({
            title: '上传成功',
            showCancel: false,
            success(res) {
              if (res.confirm) {
                wx.navigateTo({
                  url: '../index/index',
                })
              }
            }
          })
        }
        else{
          wx.showModal({
            title: '该记录已存在',
            showCancel: false,
            success(res) {
            }
          })
        }
      }
    })
  }
    else {
    wx.hideLoading();
    wx.showModal({
      title: '请输入对应的中文景点',
      showCancel: false,
    })
}
  },

  uploadVoice() {
    var that = this
    wx.chooseImage({
      sourceType: [],
      success: function (res) {
        wx.showLoading({
          title: "正在上传"
        })
        // 上传文件
        var uploadUrl = util.uploadFileUrl;
        wx.uploadFile({
          url: uploadUrl,
          filePath: res.tempFilePaths[0],//图片路径，如tempFilePaths[0]
          name: 'file',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            file_name: res.tempFilePaths[0]
          },
          success: function (res) {
            wx.hideLoading();
            console.log( res );
            // res为后台存储的图片名
            var obj = JSON.parse(res.data);
            that.setData({
              voiceName: obj.file_name
            });
          }
        })
      }
    })
  },

  nameInput(e) {
    this.setData({
      scenicName: e.detail.value
    })
  },

  // 生命周期函数--监听页面加载
  onLoad: function (options) {
   that = this;
   that.setData({
     pageIndex:parseInt(options.pageIndex)
   }),   
    //设置editor字体
     wx.loadFontFace({
       family: 'Pacifico',
       source: 'url("https://sungd.github.io/Pacifico.ttf")',
       success: console.log
     }),
    // 获取手机屏幕宽高
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          windowWidth: res.windowWidth,
          windowHeight: res.windowHeight,
        })
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
   onReady: function () {

   },

  /**
   * 生命周期函数--监听页面显示
   */
   onShow: function () {

   },

  /**
   * 生命周期函数--监听页面隐藏
   */
   onHide: function () {

   },

  /**
   * 生命周期函数--监听页面卸载
   */
   onUnload: function () {

   },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
   onPullDownRefresh: function () {

   },

  /**
   * 页面上拉触底事件的处理函数
   */
   onReachBottom: function () {

   },

  /**
   * 用户点击右上角分享
   */
   onShareAppMessage: function () {

   }
 })