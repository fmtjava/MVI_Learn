# MVI_Learn
使用 Jetpack 核心组件 + MVI 架构，快速实现一个 App <br />

**如果喜欢的话希望给个 `Star` 或 `Fork` ^_^ ，谢谢**

# 项目截图
<div style="float:right">
  <img src="http://m.qpic.cn/psc?/V526iEgm3HgG9w0K6aQL2X9HJE4OnV96/ruAMsa53pVQWN7FLK88i5tuV.xcdG5PbBcujY8bsNV2YQrFFuZzstX67drF5NHv*WIYlz8KjbgNX.dvICL0OzGJlgVX1JnxYChI4IAcfX8g!/b&bo=gAIkBd0EAAoDZzI!&rf=viewer_4" width="255"/>&nbsp;&nbsp;&nbsp;
  <img src="http://m.qpic.cn/psc?/V526iEgm3HgG9w0K6aQL2X9HJE4OnV96/ruAMsa53pVQWN7FLK88i5tuV.xcdG5PbBcujY8bsNV2R.*zPNX*OYuKdnC1j5brmrUkkvSGM4wa8kKXTBOFO1eFKA81X3XxvzBqJ2ZKS.NY!/b&bo=gAIkBd0EAAoDVwI!&rf=viewer_4" width="255"/>&nbsp;&nbsp;&nbsp;
  <img src="http://m.qpic.cn/psc?/V526iEgm3HgG9w0K6aQL2X9HJE4OnV96/ruAMsa53pVQWN7FLK88i5tuV.xcdG5PbBcujY8bsNV2ET3wphonD00PzOMlFFbX.1b5KKfCo1H3L6FlTjAg6j*A.a3UeyRJ4sGzGMQUWZsY!/b&bo=gAIkBd0EAAoDRxI!&rf=viewer_4" width="255"/>
</div>

<br/>

# 核心架构
<div style="float:right">
  <img src="http://m.qpic.cn/psc?/V526iEgm3HgG9w0K6aQL2X9HJE4OnV96/ruAMsa53pVQWN7FLK88i5uh.esy8dQlWqrURok1A5d1zpBnGz8lmOXdQ7ZghPKPMCc9xABkfkaSoDiUPbxe92dx5pWzpRtfRT4r3xBtQuag!/b&bo=gAK1AYACtQEBFzA!&rf=viewer_4"/>
</div>

## 核心组件
   - Model： 与MVVM中的Model不同的是，MVI的Model主要指UI状态（State）。例如页面加载状态、控件位置等都是一种UI状态。
   - View：  与其他MVX中的View一致，可能是一个Activity或者任意UI承载单元。MVI中的View通过订阅Model的变化实现界面刷新。
   - Intent：此Intent不是Activity的Intent，用户的任何操作都被包装成Intent后发送给Model层进行数据请求。
  
# 下载体验
 - 点击[![](https://img.shields.io/badge/Download-apk-green.svg)](https://www.pgyer.com/8SDl)
 - 下方二维码下载(每日上限100次，如达到上限，还是 clone 源码吧！✧(≖ ◡ ≖✿))）<br/>
   <img src="https://www.pgyer.com/app/qrcode/8SDl"/>  
   
# 感谢
  - [flutter_trip](https://github.com/wkl007/flutter_trip)
  - [JiaoZiVideoPlayer](https://github.com/Jzvd/JZVideo)
  - [MultiStatePage](https://github.com/Zhao-Yan-Yan/MultiStatePage)
  - [AgentWeb](https://github.com/Justson/AgentWeb)
  - [开放API-2.0](https://api.apiopen.top/)

 # 关于我
  - WX：fmtjava
  - QQ：2694746499
  - Email：2694746499@qq.com
  - Github：https://github.com/fmtjava

 # 声明
  项目中的API来自开放API-2.0官网，纯属学习交流使用，不得用于商业用途！   
  
 # License

 Copyright (c) 2022 fmtjava

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 
   
