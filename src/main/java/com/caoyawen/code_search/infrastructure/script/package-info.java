/**
 * script处理运行shell脚本的需求，常见的脚本由{@link com.caoyawen.code_search.infrastructure.template.Render}动态生成，script支持单个脚本执行，也支持并发的批量执行。
 * <p>其中的核心概念包括.</p>
 * <ul>
 *     <li><b>执行器(Run)</b> 执行器支持单脚本执行{@link com.caoyawen.code_search.infrastructure.script.Run#script(java.lang.String, com.caoyawen.code_search.infrastructure.script.Env)}和多脚本并发执行{@link com.caoyawen.code_search.infrastructure.script.Run#multiScripts(com.caoyawen.code_search.infrastructure.script.Inputs, int)}</li>
 *     <li><b>多个输入(Inputs)</b> 在并发执行的时候，需要将输入用Inputs定义</li>
 *     <li><b>单个输入(Input)</b> Inputs中的一个元素</li>
 *     <li><b>输出(Output)</b> 返回输出结果</li>
 *     <li><b>环境变量(Env)</b> 定义包括环境变量，work directory等概念</li>
 * </ul>
 * 常见例子
 * <pre>
 *      Run run = new Run();
 *      Output output = run.script("echo 'hello world'", new Env());
 *      System.out.println(output.stdOutput.get(0));
 * </pre>
 * 得到 hello world
 */
package com.caoyawen.code_search.infrastructure.script;