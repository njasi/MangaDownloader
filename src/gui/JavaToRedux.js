/**
 *  produces a janky way for java to tell js what to render
 *  (using jxBrowser: Browser.executeJavaScript("dispatch('jsonStringOfActionForDispatch')"))
 *
 *  I didnt want to host a local server to connect them because thats boring
 *
 *  should work idk havent tested yet
 *
 *  this would be unsafe for a web app (users can just dispatch), but since its
 *  a gui for a personal app who cares there isn't anything to hack
 *
 *  i also like this because it's funny
 *
 *  may change later
 */

export default store => {
  window.dispatch = json => {
    store.dispatch(JSON.parse(json))
  }
}
