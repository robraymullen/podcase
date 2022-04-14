import { ListItem, ListItemText, ListItemAvatar, Grid, Box, Typography } from '@mui/material';
import React from "react";
import { ITunesResult } from '../../Types';

interface SearchResultItemProps {
    children: never[];
    key: string;
    item: ITunesResult;
}

const SearchResultItem = ({ item }: SearchResultItemProps) => {

    console.log(item);

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
                        </Grid>
                    </Grid>
                </Grid>
            </ListItem>
        </div>
    )
}

export default SearchResultItem;