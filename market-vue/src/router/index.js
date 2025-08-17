import VueRouter from "vue-router";
import axios from "axios";


const routes = [
    //用户端登录
    {
        path: '/userLogin',
        name: 'userLogin',
        component: () => import('../views/Login/UserLogin')
    },

    //用户端界面路由设置
    {
        path: '/user',
        name: 'userMain',
        component: () => import('../views/userMain'),
        //需要登录以及身份认证才能访问以下路由
        meta: {authRequired: true},
        children: [

            {
                path: '/user/HomeOne',
                name: "HomeOne",
                component: () => import('../components/user/HomeOne'),
            },

            {
                path: '/user/HelpOne',
                name: 'HelpOne',
                component: () => import('../components/user/HelpOne'),
            },
            {
                path: '/user/HelpTwo',
                name: 'HelpTwo',
                component: () => import('../components/user/HelpTwo'),
            },
            {
                path: '/user/HomeTwo',
                component: () => import('../components/user/HomeTwo'),
            },
            {
                path: '/user/ShoppingCart',
                name: "ShoppingCart",
                component: () => import('../components/user/ShoppingCart'),
            },
            {
                path: '/user/personInfo',
                name: "personInfo",
                component: () => import('../components/user/personInfo'),
            },
            {
                path: '/user/PersonChange',
                name: "PersonChange",
                component: () => import('../components/user/PersonChange'),
            },
            {
                path: '/user/MessageCenter',
                name: "MessageCenter",
                component: () => import('../components/user/MessageCenter'),
            },
            {
                path: '/user/OrderCenter',
                name: "OrderCenter",
                component: () => import('../components/user/OrderCenter'),
            },
            {
                path: '/user/OrderNei',
                name: "OrderNei",
                component: () => import('../components/user/OrderNei'),
            },
            {
                path: '/user/GoodInformation',
                name: "GoodInformation",
                component: () => import('../components/user/GoodInformation'),
            },
            {
                path: '/user/SubmitOrder',
                name: "/SubmitOrder",
                component: () => import('../components/user/SubmitOrder'),
            },
            {
                path: '/user/GoodInformation',
                name: "/GoodInformation",
                component: () => import('../components/user/GoodInformation'),
            },
            {
                path: '/user/BuyInformation',
                name: "/BuyInformation",
                component: () => import('../components/user/BuyInformation'),
            },
            // {
            //     path: '/user/tableRule',
            //     name: 'tableRule',
            //     component: () => import('../components/user/TableRule'),
            // },
            // {
            //     path: '/user/ReportTxt',
            //     name: "/ReportTxt",
            //     component: () => import('../components/user/ReportTxt'),
            // },
            // {
            //     path: '/user/AboutUs',
            //     name: "/AboutUs",
            //     component: () => import('../components/user/AboutUs'),
            // },
            // {
            //     path: '/user/TableRule',
            //     name: "/TableRule",
            //     component: () => import('../components/user/TableRule'),
            // },
        ]
    },

    //管理员登录路由
    {
        path: '/admin',
        name: 'adminLogin',
        component: () => import('../views/Login/AdminLogin')
    },


    //后台界面路由设置
    {
        path: '/adminMain',
        name: 'adminMain',
        component: () => import('../views/adminMain'),
        //需要登录以及身份认证才能访问以下路由
        meta: {authRequired: true},
        children: [
            {
                path: '/adminMain/home',
                name: 'adminHome',
                meta: {
                    title: '首页'
                },
                component: () => import('../components/admin/home/home')
            },
            {
                path: '/adminMain/personController',
                name: 'adminPersonController',
                meta: {
                    title: '用户管理'
                },
                component: () => import('../components/admin/person/personController')
            },
            {
                path: '/adminMain/orderController',
                name: 'adminOrderController',
                meta: {
                    title: '订单管理'
                },
                component: () => import('../components/admin/order/orderController')
            },
            {
                path: '/adminMain/commentController',
                name: 'adminCommentController',
                meta: {
                    title: '评论管理'
                },
                component: () => import('../components/admin/comment/commentController')
            },
            {
                path: '/adminMain/goodController',
                name: 'adminGoodController',
                meta: {
                    title: '商品管理'
                },
                component: () => import('../components/admin/good/goodController')
            },
            {
                path: '/adminMain/needGoodsController',
                name: 'adminNeedGoodsController',
                meta: {
                    title: '求购管理'
                },
                component: () => import('../components/admin/needGoods/needGoodsController')
            },
            {
                path: '/adminMain/categoryController',
                name: 'adminCategoryController',
                meta: {
                    title: '分类管理'
                },
                component: () => import('../components/admin/category/categoryController')
            },
            {
                path: '/adminMain/logController',
                name: 'adminLogController',
                meta: {
                    title: '日志管理'
                },
                component: () => import('../components/admin/log/logController')
            },
            {
                path: '/adminMain/carouselSetting',
                name: 'adminCarouselSetting',
                meta: {
                    title: '轮播图设置'
                },
                component: () => import('../components/admin/carousel/carouselSetting')
            },
            {
                path: '/adminMain/hotSaleNearbySetting',
                name: 'adminHotSaleNearbySetting',
                meta: {
                    title: '附近热卖配置'
                },
                component: () => import('../components/admin/hotSaleNearby/hotSaleNearbySetting')
            },
            {
                path: '/adminMain/passwordSetting',
                name: 'adminPasswordSetting',
                meta: {
                    title: '个人信息'
                },
                component: () => import('../components/admin/password/passwordController')
            },
            {
                path: '/adminMain/managerController',
                name: 'adminManagerController',
                meta: {
                    title: '管理员'
                },
                component: () => import('../components/admin/manager/managerController')
            },
        ]
    }
]


const router = new VueRouter({
    scrollBehavior(to, from, savedPosition) {
        return {x: 0, y: 0}; // 跳转后滚动到页面顶部
    },
    mode: 'history',
    routes
})

// 全局路由守卫
// 当跳转的页面不是登录页面且身份验证失败时默认跳转到登录页面，否则跳转成功
router.beforeEach((to, from, next) => {
        //如果没有token 则身份认证失败
        //如果前往的界面是登录界面 即想要实现退出登录功能，则在推出之前先情况当前token，避免被入侵
        if (to.name === 'adminLogin' || to.name === 'userLogin') {
            localStorage.removeItem("token");
            localStorage.removeItem("person");
            localStorage.removeItem("admin");
            localStorage.removeItem("passwordNoMD5");
            next();
        } else if (to.path === '/' && !to.meta.authRequired) {
            next(false); // 停止当前导航
            window.location.href = '/user/HomeOne'; // 跳转到/user/HomeOne
            return; // 停止继续执行后续代码
        } else {
            let token = localStorage.getItem("token");
            // console.log(token);
            if (token === null || token === '' || token === undefined) {
                if (to.name.substring(0, 5) === 'admin') {
                    // console.log(to.name);
                    next({name: 'adminLogin'})
                } else {
                    next({name: 'userLogin'})
                }
            } else {
                // 检验token是否正确
                axios({
                    url: 'http://localhost:8090/checkToken', //在controller中写一个接口用来token校验
                    method: 'get',
                    //将token信息保存在header里
                    headers: {
                        token: token
                    }
                }).then((response) => {
                    if (!response.data) {
                        console.log('检验失败')
                        router.replace({path: '/'}) // 如果token失效,返回到登录页面
                    }
                })
                next();
            }
        }
    }
)


export function resetRouter() {
    router.matcher = new VueRouter({
        mode: 'history',
        routes: []
    }).matcher
}

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
//导出router
export default router