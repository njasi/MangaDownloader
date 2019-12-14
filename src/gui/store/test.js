/**
 * ACTION TYPES
 */
const SET_TEST = 'SET_TEST'

export const setTest = test => ({type: SET_TEST, test})

const init = 'EMPTY'

export default function(state = init, action) {
  switch (action.type) {
    case SET_TEST:
      return action.test
    default:
      return state
  }
}
