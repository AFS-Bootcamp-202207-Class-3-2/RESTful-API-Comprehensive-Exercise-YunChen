# Homework for weekend
> CompanyMapper和EmployeeMapper被作为util放在utils下
>
> 已经实现了使用Response在Employee和Company的Controller用于过滤敏感数据和用于满足前端的需求
> 


# 对于为什么多了CompanyPageResponse和EmployeePageResponse
> 因为Jpa的分页查询放回的Page实际上的实现类PageImpl中有属性
>
> - totalPage
> - totalElement
> - last
> - hasNext()
> - hasPrevious()
> - hasContent()
>
>多封装这一层是考虑到方便前端进行分页的操作
>


# 拓展

> 另外我会新建一条分支实践开闭原则，将两个Mapper变为一个MapperDtoUtil
>
> 原来的我也会留着给予参考打分   