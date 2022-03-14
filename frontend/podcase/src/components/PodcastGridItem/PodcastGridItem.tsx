import { SubscribedPodcast } from "../../Types";
import ReactHtmlParser from 'react-html-parser';

const PodcastGridItem = (podcast: SubscribedPodcast) => {
    return (
        <div>
            <h2>{podcast.name}</h2>
            <img src={podcast.imageUrl} width="50" height="50"/>
            <p>
                {ReactHtmlParser(podcast.description)}
            </p>
        </div>
    )
}

export default PodcastGridItem;
