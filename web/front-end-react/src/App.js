import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import React, { Suspense, lazy } from 'react';

const Home = lazy(() => import('./routes/Home'));
const Register = lazy(() => import('./routes/Register'));

const App = () => (
  <div className="row justify-content-sm-center">
    <div className="col-lg-8 col-xl-7">
      <Router>
        <Suspense fallback={<div>Loading...</div>}>
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/register" component={Register} />
          </Switch>
        </Suspense>
      </Router>
    </div>
  </div>

);

export default App