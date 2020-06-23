$(function(){
    $('#query_content').autoComplete({
        resolverSettings: {
            url: '/source_code/suggestion/search'
        }
    });
    console.log("ready?");
});
