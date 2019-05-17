import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import React, { Suspense, lazy } from 'react';

const Home = lazy(() => import('./routes/Home'));
const Register = lazy(() => import('./routes/Register'));
const Learn = lazy(() => import('./routes/Learn'));
const Identify = lazy(() => import('./routes/Identify'));
const Persons = lazy(() => import('./routes/admin/Persons'));

const App = () => (
  <div className="row justify-content-sm-center">
    <div className="col-lg-8 col-xl-7">
      <Router>
        <Suspense fallback={<div>Loading...</div>}>
          <Switch>

            {/* User */}
            <Route exact path="/" component={Home} />
            <Route path="/register" component={Register} />
            <Route path="/learn" component={Learn} />
            <Route path="/identify" component={Identify} />
            {/* admin */}
            <Route path="/admin/person/list" component={Persons} />
          </Switch>
        </Suspense>
      </Router>
    </div>
  </div>

);

export default App