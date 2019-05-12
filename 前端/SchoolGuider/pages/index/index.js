//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  selectLanguage1() {
    wx.navigateTo({
      url: "../SelectLanguage/SelectLanguage?mode=add"
    })
  },
  selectLanguage2() {
    wx.navigateTo({
      url: "../SelectLanguage/SelectLanguage?mode=change"
    })
  },
  scanQRCode() {
    wx.navigateTo({
      url: "../scan/scan"
    })
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  uploadPic:function(e){
    wx.navigateTo({
      url: '../UploadPic/UploadPic',
    })
  },

  scan: function (options) {
    var that = this;
    wx.showActionSheet({
      itemList: ['从手机相册选择', '扫码'],
      success: function (res) {
        that.setData({
          tapIndex: res.tapIndex
        })
        if (that.data.tapIndex == '0') {
          wx.scanCode({
            success: (res) => {
              console.log(res);
              app.globalData.recordId = res.result.split(":")[1];
              wx.navigateTo({
                url: "../Language/Language"
              })
            },
            fail: (res) => {
              wx.showToast({
                title: '扫描失败',
                icon: "none",
                duration: 1000
              })
            },
          })
        }
        else if (that.data.tapIndex == '1') {
          wx.scanCode({
            onlyFromCamera: true,
            success(res) {
              app.globalData.recordId = res.result.split(":")[1];
              wx.navigateTo({
                url: "../Language/Language"
              })
            },
            fail: function(res){
              wx.showToast({
                title: '扫描失败',
                icon: "none",
                duration: 1000
              })
            }
          })
        }
      },
      fail: function (res) {
        console.log(res.errMsg)
      }
    })
  },
  fail: function (res) {
    console.log(res.errMsg)
  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
