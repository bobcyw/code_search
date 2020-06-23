/**
 * 处理源代码相关的功能。
 * <p>
 *     这些功能建立在{@link com.caoyawen.code_search.infrastructure.git.project}之上
 * </p>
 * 整个源代码的管理分为
 * <ul>
 *     <li><b>管理(Manager)</b> 负责将代码从Source转移到Sink中</li>
 *     <li><b>源头(Source)</b> 表示代码的来源，gitlab，github等</li>
 *     <li><b>存储(Sink)</b> 表示代码要存储的地方，本地磁盘等</li>
 * </ul>
 * 整个服务配置好以后，通过 {@link com.caoyawen.code_search.domain.code.base.Manager#update()} 来驱动代码从Source更新到Sink
 */
package com.caoyawen.code_search.domain.code;