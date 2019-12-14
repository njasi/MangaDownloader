import React from 'react'
import Test from './Components/Test'

export default () => {
  return (
    <>
      <h1>Hello there</h1>
      <button type="button" onClick={() => test()}>
        Invoke java?
      </button>
      <Test />
    </>
  )
}
