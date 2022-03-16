import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';
import { getUserSubscriptions } from '../../services/PodcaseAPIService';
import { SubscribedPodcast } from '../../Types';
import PodcastGridItem from '../PodcastGridItem/PodcastGridItem';

const PodcastGrid = () => {

    const [podcasts, setPodcasts] = useState<SubscribedPodcast[]>([]);

    useEffect(() => {
        getUserSubscriptions(1, setPodcasts, () => {});
    }, []);

    return (
        <div>
            <Grid container spacing={2}>
            {
                podcasts.map(podcast => {
                    return <PodcastGridItem id={podcast.id} description={podcast.description} imageUrl={podcast.imageUrl} name={podcast.name}></PodcastGridItem>
                })
            }
            </Grid>
            
        </div>
    )
}

export default PodcastGrid;
