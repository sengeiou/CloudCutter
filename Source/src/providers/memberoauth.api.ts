import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { RequestOptions } from '@angular/http';
import { ApiConfig } from '../app/api.config'
@Injectable()
export class MemberoauthApi {

    constructor(public http: Http) {

    }


    public wechatappoauth(data, showLoadingModal: boolean = true) {
        var url = ApiConfig.getApiUrl() + 'memberoauth/wechatappoauth';
        var headers = ApiConfig.GetHeader(url, data);
        let options = new RequestOptions({ headers: headers });
        let body = ApiConfig.ParamUrlencoded(data);
        let loading = null;

        if (showLoadingModal) {
            loading = ApiConfig.GetLoadingModal();
        }

        return this.http.post(url, body, options).toPromise()
            .then((res) => {
                if (ApiConfig.DataLoadedHandle('memberoauth/wechatappoauth', data, res)) {
                    if (showLoadingModal) {
                        ApiConfig.DimissLoadingModal();
                    }
                    if (res==null) {
                        return null;
                    }
                    return res.json();
                } else {
                    return Promise.reject(res);
                }
            })
            .catch(err => {
                if (showLoadingModal) {
                    ApiConfig.DimissLoadingModal();
                }
                return ApiConfig.ErrorHandle('memberoauth/wechatappoauth', data, err);
            });
    }

}
