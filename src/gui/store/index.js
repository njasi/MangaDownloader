import {createStore, combineReducers, applyMiddleware} from 'redux'
import {createLogger} from 'redux-logger'
import thunkMiddleware from 'redux-thunk'
import {composeWithDevTools} from 'redux-devtools-extension'

import test from './test'

const reducer = combineReducers({
  test,
})

// TODO eventually get rid of the logger
const middleware = composeWithDevTools(
  applyMiddleware(thunkMiddleware /*, createLogger({collapsed: true})*/)
)

const store = createStore(reducer, middleware)

export default store
export * from "./test"