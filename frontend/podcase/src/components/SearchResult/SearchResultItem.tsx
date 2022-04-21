import { ListItem, ListItemText, ListItemAvatar, Grid, Box, Typography, IconButton, Tooltip } from '@mui/material';
import React, { useContext, useEffect, useState } from "react";
import { ITunesResult, SubscribedPodcast } from '../../Types';
import AddIcon from '@mui/icons-material/Add';
import { AppContext } from '../../context/context';
import { addUserSubscriptionFromRSS } from '../../services/PodcaseAPIService';

interface SearchResultItemProps {
    children: never[];
    key: string;
    item: ITunesResult;
}

const SearchResultItem = ({ item }: SearchResultItemProps) => {

    const { state, dispatch } = useContext(AppContext);
    const [isSubscribed, setIsSubscribed] = useState<boolean>(false);
    const tooltipTitle = isSubscribed ? "Already subscribed to this podcast" : "Subscribe to podcast";

    const subscribe = () => {
        if (state.currentUser) {
            addUserSubscriptionFromRSS(item.feedUrl, item.collectionName, state.currentUser.name);
            setIsSubscribed(true);
        } else {
            console.error("Cannot add subscription without having a user selected");
        }

    };

    useEffect(() => {
        setIsSubscribed(state.userSubscriptions.find((podcast: SubscribedPodcast) => podcast.name === item.collectionName) !== undefined);
    }, []);

    return (
        <div>
            <ListItem
                sx={{ border: 1, borderColor: 'primary.main', marginBottom: "1%", width: "100%" }}>

                <Grid container spacing={2}>
                    <Grid item>
                        <Box
                            display="flex"
                            position="relative"
                            alignItems="center"
                            justifyContent="center"
                        >
                            <Box
                                marginTop="20%"
                            >
                                <ListItemAvatar>
                                    <img src={item.artworkUrl60} height="60" width="60" />
                                </ListItemAvatar>
                            </Box>

                        </Box>
                    </Grid>
                    <Grid item xs={12} sm container className="listItemNoHover">
                        <Grid item xs container direction="column" spacing={2}>
                            <Grid item xs>
                                <ListItemText  >
                                    <Typography
                                        sx={{ display: 'inline' }}
                                        component="span"
                                        variant="body2"
                                        color="text.primary"
                                    >
                                        {item.collectionName}
                                    </Typography>
                                </ListItemText>
                            </Grid>
                            <Grid item xs>
                                <Tooltip title={tooltipTitle} placement="bottom">
                                    <span>
                                        <IconButton color="primary" onClick={subscribe} disabled={isSubscribed}>
                                            <AddIcon></AddIcon>
                                        </IconButton>
                                    </span>
                                </Tooltip>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </ListItem>
        </div>
    )
}

export default SearchResultItem;