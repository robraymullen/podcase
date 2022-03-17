import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';
import { getUserSubscriptions, getAllPodcasts } from '../../services/PodcaseAPIService';
import { GridRoutes, SubscribedPodcast } from '../../Types';
import PodcastGridItem from '../PodcastGridItem/PodcastGridItem';

const PodcastGrid = (props: any) => {

    const [podcasts, setPodcasts] = useState<SubscribedPodcast[]>([]);

    useEffect(() => {
        if (props && props.state === GridRoutes.PODCAST_ALL) {
            getAllPodcasts(setPodcasts, () => {});
        } else if (props && props.state === GridRoutes.PODCAST_SUBSCRIPTION) {
            getUserSubscriptions(1, setPodcasts, () => {});
        }
        
    }, [podcasts]);

    return (
            <Grid container spacing={2} key="podcastGrid">
            {
                podcasts.map(podcast => {
                    return <PodcastGridItem key={podcast.id} id={podcast.id} description={podcast.description} imageUrl={podcast.imageUrl} name={podcast.name}></PodcastGridItem>
                })
            }
            </Grid>
    )
}

export default PodcastGrid;
