import { List, Box, CircularProgress } from '@mui/material';
import { ITunesResult, SearchResponse } from "../../Types";
import SearchResultItem from "./SearchResultItem";
import { useNavigate, useLocation } from "react-router-dom";

interface Location {
    state: {
        searchResults: SearchResponse
    }
}

const SearchResult = () => {

    const location = useLocation() as Location;
    const results = location.state && location.state.searchResults ? location.state.searchResults.results : [];
    console.log(location);

    return (
        <Box>
            {
                results && results.length > 0 ?
                    <div>
                        <List sx={{ bgcolor: 'background.paper' }}>
                            {
                                results.map((result: ITunesResult) => {
                                    return <SearchResultItem key={result.collectionId+""} 
                                        item={result}
                                    >
                                    </SearchResultItem>
                                })
                            }
                        </List>
                    </div>

                    : <CircularProgress />
            }
        </Box>
    )
}

export default SearchResult;