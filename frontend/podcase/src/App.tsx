import React from 'react';
import logo from './logo.svg';
import './App.css';
import { getUserSubscriptions } from './services/PodcaseAPIService';
import { Podcast } from './Types';
import PodcastGrid from './components/PodcastGrid/PodcastGrid';

function App() {

  const empty = getUserSubscriptions(1, (result:Podcast[]) => {console.log(result)}, () => {});
  console.log(empty);

  return (
    <div className="App">
      <PodcastGrid></PodcastGrid>
    </div>
  );
}

export default App;
