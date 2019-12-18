import React from 'react'
import {connect} from 'react-redux'

const Test = props => {
  return <h5>{props.test}</h5>
}

const mapState = state => ({
  test: state.test
})

export default connect(mapState)(Test)
